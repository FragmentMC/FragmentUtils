package stanuwu.fragmentutils.render.font;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FontManager {
    private final TTFFontRenderer defaultFont;
    private final FontManager instance;
    public final HashMap<String, TTFFontRenderer> fonts;

    public FontManager getInstance() {
        return this.instance;
    }

    public TTFFontRenderer getFont(final String key) {
        return this.fonts.getOrDefault(key, this.defaultFont);
    }

    private void createNewFont(String fontName, String fontFile, int size, ExecutorService executor, ConcurrentLinkedQueue<TextureData> textureQueue) throws IOException, FontFormatException {
        final InputStream fontInStream = this.getClass().getResourceAsStream("/assets/fragment_utils/font/" + fontFile + ".ttf");

        if (fontInStream != null) {
            Font createdFont = Font.createFont(0, fontInStream);
            createdFont = createdFont.deriveFont(Font.PLAIN, (float) size);
            this.fonts.put(fontName + " " + size, new TTFFontRenderer(executor, textureQueue, createdFont));
        }
    }

    public FontManager() {
        this.fonts = new HashMap<>();
        this.instance = this;
        final ConcurrentLinkedQueue<TextureData> textureQueue = new ConcurrentLinkedQueue<>();
        final ExecutorService executor = Executors.newFixedThreadPool(2);
        this.defaultFont = new TTFFontRenderer(executor, textureQueue, new Font("Arial", Font.PLAIN, 16));

        try {
            for (int size : new int[]{20, 24, 32}) {
                this.createNewFont("Roboto", "Roboto", size, executor, textureQueue);
            }
            for (int size : new int[]{92}) {
                this.createNewFont("Righteous", "Righteous", size, executor, textureQueue);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }

        executor.shutdown();

        while (!executor.isTerminated()) {
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (!textureQueue.isEmpty()) {
                final TextureData textureData = textureQueue.poll();

                RenderSystem.bindTexture(textureData.getTextureId());
                RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
                RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
                GlStateManager._texImage2D(GL11.GL_TEXTURE_2D, GL11.GL_ZERO, GL11.GL_RGBA, textureData.getWidth(), textureData.getHeight(), GL11.GL_ZERO, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, textureData.getBuffer().asIntBuffer());
            }
        }

        Runtime.getRuntime().gc();
        System.gc();
    }
}
