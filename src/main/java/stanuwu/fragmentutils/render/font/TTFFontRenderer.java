package stanuwu.fragmentutils.render.font;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.math.Matrix4f;
import org.lwjgl.BufferUtils;
import stanuwu.fragmentutils.render.RenderHelper;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

public class TTFFontRenderer {
    private static TTFFontRenderer instance;
    private final boolean antiAlias;
    private final Font font;
    private boolean fractionalMetrics;
    private CharacterData[] regularData;
    private CharacterData[] boldData;
    private CharacterData[] italicsData;
    private final int[] colorCodes;
    private static final int MARGIN = 4;
    private static final String COLOR_INVOKER = "§";
    private static int RANDOM_OFFSET;
    private static Color shadowColor = new Color(0, 0, 0, 100);

    private Color color = new Color(0, 0, 0, 0);

    public TTFFontRenderer(final ExecutorService executorService, final ConcurrentLinkedQueue<TextureData> textureQueue, final Font font) {
        this(executorService, textureQueue, font, 256);
        instance = this;
    }

    public TTFFontRenderer(final ExecutorService executorService, final ConcurrentLinkedQueue<TextureData> textureQueue, final Font font, final int characterCount) {
        this(executorService, textureQueue, font, characterCount, true);
        instance = this;
    }

    public TTFFontRenderer(final ExecutorService executorService, final ConcurrentLinkedQueue<TextureData> textureQueue, final Font font, final boolean antiAlias) {
        this(executorService, textureQueue, font, 256, antiAlias);
        instance = this;
    }

    public TTFFontRenderer(final ExecutorService executorService, final ConcurrentLinkedQueue<TextureData> textureQueue, final Font font, final int characterCount, final boolean antiAlias) {
        instance = this;
        this.fractionalMetrics = false;
        this.colorCodes = new int[32];
        this.font = font;
        this.fractionalMetrics = true;
        this.antiAlias = antiAlias;
        final int[] regularTexturesIds = new int[characterCount];
        final int[] boldTexturesIds = new int[characterCount];
        final int[] italicTexturesIds = new int[characterCount];

        for (int i = 0; i < characterCount; ++i) {
            regularTexturesIds[i] = GlStateManager._genTexture();
            boldTexturesIds[i] = GlStateManager._genTexture();
            italicTexturesIds[i] = GlStateManager._genTexture();
        }

        executorService.execute(() -> this.regularData = this.setup(new CharacterData[characterCount], regularTexturesIds, textureQueue, 0));
        executorService.execute(() -> this.boldData = this.setup(new CharacterData[characterCount], boldTexturesIds, textureQueue, 1));
        executorService.execute(() -> this.italicsData = this.setup(new CharacterData[characterCount], italicTexturesIds, textureQueue, 2));
    }

    public static TTFFontRenderer getInstance() {
        return instance;
    }

    private CharacterData[] setup(final CharacterData[] characterData, final int[] texturesIds, final ConcurrentLinkedQueue<TextureData> textureQueue, final int type) {
        this.generateColors();
        final Font derivedFont = this.font.deriveFont(type);
        final BufferedImage utilityImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D utilityGraphics = (Graphics2D) utilityImage.getGraphics();
        utilityGraphics.setFont(derivedFont);
        final FontMetrics fontMetrics = utilityGraphics.getFontMetrics();

        for (int index = 0; index < characterData.length; ++index) {
            final char character = (char) index;
            final Rectangle2D characterBounds = fontMetrics.getStringBounds(character + "", utilityGraphics);
            final float width = (float) characterBounds.getWidth() + 8.0f;
            final float height = (float) characterBounds.getHeight();
            final BufferedImage characterImage = new BufferedImage((int) Math.ceil(width), (int) Math.ceil(height), BufferedImage.TYPE_INT_ARGB);
            final Graphics2D graphics = (Graphics2D) characterImage.getGraphics();
            graphics.setFont(derivedFont);
            graphics.setColor(Color.WHITE);

            if (this.antiAlias) {
                graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, this.fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
            }

            graphics.drawString(character + "", 4, fontMetrics.getAscent());

            final int textureId = texturesIds[index];
            this.createTexture(textureId, characterImage, textureQueue);
            characterData[index] = new CharacterData(character, (float) characterImage.getWidth(), (float) characterImage.getHeight(), textureId);
        }

        return characterData;
    }

    private void createTexture(final int textureId, final BufferedImage image, final ConcurrentLinkedQueue<TextureData> textureQueue) {
        final int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        final ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

        for (int y = 0; y < image.getHeight(); ++y) {
            for (int x = 0; x < image.getWidth(); ++x) {
                int pixel = pixels[y * image.getWidth() + x];
                switch (pixel) {
                    case 0:
                        pixel = 16777215;
                }
                buffer.put((byte) (pixel >> 16 & 0xFF));
                buffer.put((byte) (pixel >> 8 & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) (pixel >> 24 & 0xFF));
            }
        }

        buffer.flip();
        textureQueue.add(new TextureData(textureId, image.getWidth(), image.getHeight(), buffer));
    }

    public int drawString(MatrixStack poseStack, final String text, final float x, final float y, final int color) {
        return this.renderString(poseStack, text, x, y, color, false);
    }

    public void drawCenteredString(MatrixStack poseStack, final String text, final float x, final float y, final int color) {
        final float width = this.getWidth(text) / 2.0f;
        this.renderString(poseStack, text, x - width, y, color, false);
    }

    public void drawCenteredStringWithShadow(MatrixStack poseStack, final String text, final float x, final float y, final int color) {
        final float width = this.getWidth(text) / 2.0f;
        poseStack.translate(0.5, 0.5, 0);
        this.renderString(poseStack, text, x - width, y, shadowColor.getRGB(), true);
        poseStack.translate(-0.5, -0.5, 0);
        this.renderString(poseStack, text, x - width, y, color, false);
    }

    public void drawStringWithShadow(MatrixStack poseStack, final String text, final float x, final float y, final int color) {
        poseStack.translate(0.5, 0.5, 0);
        this.renderString(poseStack, text, x, y, shadowColor.getRGB(), true);
        poseStack.translate(-0.5, -0.5, 0);
        this.renderString(poseStack, text, x, y, color, false);
    }

    private int renderString(MatrixStack poseStack, final String text, float x, float y, final int color, final boolean shadow) {
        if (text.length() == 0) {
            return 0;
        }
        RenderHelper.setShaderColor(Color.WHITE);
        poseStack.push();
        poseStack.scale(0.5f, 0.5f, 1);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(770, 771);
        x -= 2.0f;
        y -= 2.0f;
        x += 0.5f;
        y += 0.5f;
        x *= 2.0f;
        y *= 2.0f;
        CharacterData[] characterData = this.regularData;
        boolean underlined = false;
        boolean strikethrough = false;
        boolean obfuscated = false;
        final int length = text.length();
        final double multiplier = 255.0 * (shadow ? 4 : 1);
        final Color c = new Color(color);
        this.color = c;
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);

        for (int i = 0; i < length; ++i) {
            char character = text.charAt(i);
            final char previous = (i > 0) ? text.charAt(i - 1) : '.';
            String pString = new String(new char[]{previous});

            if (pString != COLOR_INVOKER) {
                if (pString == COLOR_INVOKER && i < length) {
                    int index = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));

                    if (index < 16) {
                        obfuscated = false;
                        strikethrough = false;
                        underlined = false;
                        characterData = this.regularData;

                        if (index < 0 || index > 15) {
                            index = 15;
                        }

                        if (shadow) {
                            index += 16;
                        }

                        final int textColor = this.colorCodes[index];
                        this.color = new Color((textColor >> 16), (textColor >> 8 & 0xFF), (textColor & 0xFF), (color >> 24 & 0xFF));
                    } else if (index == 16) {
                        obfuscated = true;
                    } else if (index == 17) {
                        characterData = this.boldData;
                    } else if (index == 18) {
                        strikethrough = true;
                    } else if (index == 19) {
                        underlined = true;
                    } else if (index == 20) {
                        characterData = this.italicsData;
                    } else if (index == 21) {
                        obfuscated = false;
                        strikethrough = false;
                        underlined = false;
                        characterData = this.regularData;
                        this.color = new Color((float) (1.0 * (shadow ? 0.25 : 1.0)), (float) (1.0 * (shadow ? 0.25 : 1.0)), (float) (1.0 * (shadow ? 0.25 : 1.0)), (float) ((color >> 24 & 0xFF) / 255.0));
                    }
                } else if (character <= '\u00ff') {
                    if (obfuscated) {
                        character += (char) TTFFontRenderer.RANDOM_OFFSET;
                    }

                    this.drawChar(poseStack, character, characterData, x, y);
                    final CharacterData charData = characterData[character];

                    if (strikethrough) {
                        this.drawLine(new Vector2f(0.0f, charData.height / 2.0f), new Vector2f(charData.width, charData.height / 2.0f), 3.0f);
                    }

                    if (underlined) {
                        this.drawLine(new Vector2f(0.0f, charData.height - 15.0f), new Vector2f(charData.width, charData.height - 15.0f), 3.0f);
                    }

                    x += charData.width - 8.0f;
                }
            }
        }

        poseStack.pop();
        this.color = new Color(0, 0, 0, 0);
        RenderSystem.disableBlend();

        RenderSystem.bindTexture(0);
        return (int) x;
    }

    public float getWidth(final String text) {
        float width = 0.0f;
        CharacterData[] characterData = this.regularData;

        for (int length = text.length(), i = 0; i < length; ++i) {
            final char character = text.charAt(i);
            final char previous = (i > 0) ? text.charAt(i - 1) : '.';
            String pString = new String(new char[]{previous});

            if (pString != COLOR_INVOKER) {
                if (pString == COLOR_INVOKER && i < length) {
                    final int index = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));

                    if (index == 17) {
                        characterData = this.boldData;
                    } else if (index == 20) {
                        characterData = this.italicsData;
                    } else if (index == 21) {
                        characterData = this.regularData;
                    }
                } else if (character <= '\u00ff') {
                    final CharacterData charData = characterData[character];
                    width += (charData.width - 8.0f) / 2f;
                }
            }
        }

        return width + 2f;
    }

    public float getHeight(final String text) {
        float height = 0.0f;
        CharacterData[] characterData = this.regularData;

        for (int length = text.length(), i = 0; i < length; ++i) {
            final char character = text.charAt(i);
            final char previous = (i > 0) ? text.charAt(i - 1) : '.';
            String pString = new String(new char[]{previous});

            if (pString != COLOR_INVOKER) {
                if (pString == COLOR_INVOKER && i < length) {
                    final int index = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));

                    if (index == 17) {
                        characterData = this.boldData;
                    } else if (index == 20) {
                        characterData = this.italicsData;
                    } else if (index == 21) {
                        characterData = this.regularData;
                    }
                } else if (character <= '\u00ff') {
                    final CharacterData charData = characterData[character];
                    height = Math.max(height, charData.height);
                }
            }
        }

        return height / 2.0f - 2.0f;
    }

    public float getMonoHeight() {
        return this.getHeight("I");
    }

    private void drawChar(MatrixStack poseStack, final char character, final CharacterData[] characterData, float x, float y) {
        final CharacterData charData = characterData[character];
        charData.bind();

        int z = 0;

        Matrix4f matrix = poseStack.peek().getPositionMatrix();
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();

        bufferbuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_TEXTURE_COLOR);
        bufferbuilder.vertex(matrix, x, y + charData.height, (float) z)
                .texture(0, 1)
                .color(this.color.getRGB())
                .next();
        bufferbuilder.vertex(matrix, x + charData.width, y + charData.height, (float) z)
                .texture(1, 1)
                .color(this.color.getRGB())
                .next();
        bufferbuilder.vertex(matrix, x + charData.width, y, (float) z)
                .texture(1, 0)
                .color(this.color.getRGB())
                .next();
        bufferbuilder.vertex(matrix, x, y, (float) z)
                .texture(0, 0)
                .color(this.color.getRGB())
                .next();
        bufferbuilder.end();
        BufferRenderer.draw(bufferbuilder);
    }

    private void drawLine(final Vector2f start, final Vector2f end, final float width) {
        int z = 0;

        RenderSystem.disableTexture();
        Tessellator tesselator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION_COLOR);

        bufferBuilder.vertex(start.getX(), start.getY(), z).color(this.color.getRGB()).next();
        bufferBuilder.vertex(end.getX(), end.getY(), z).color(this.color.getRGB()).next();

        tesselator.draw();
        RenderSystem.enableTexture();
    }

    private void generateColors() {
        for (int i = 0; i < 32; ++i) {
            final int thingy = (i >> 3 & 0x1) * 85;
            int red = (i >> 2 & 0x1) * 170 + thingy;
            int green = (i >> 1 & 0x1) * 170 + thingy;
            int blue = (i >> 0 & 0x1) * 170 + thingy;

            if (i == 6) {
                red += 85;
            }

            if (i >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }

            this.colorCodes[i] = ((red & 0xFF) << 16 | (green & 0xFF) << 8 | (blue & 0xFF));
        }
    }

    static {
        TTFFontRenderer.RANDOM_OFFSET = 1;
    }

    class CharacterData {
        public char character;
        public float width;
        public float height;
        private int textureId;

        public CharacterData(final char character, final float width, final float height, final int textureId) {
            this.character = character;
            this.width = width;
            this.height = height;
            this.textureId = textureId;
        }

        public void bind() {
            RenderSystem.setShaderTexture(0, this.textureId);
        }
    }
}

