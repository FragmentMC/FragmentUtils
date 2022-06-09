package stanuwu.fragmentutils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import stanuwu.fragmentutils.Utils.ColorHelper;

import java.awt.*;

public class RenderHelper {
    public static void setShaderColor(int r, int g, int b, int a) {
        RenderSystem.setShaderColor((float) r / 255, (float) g / 255, (float) b / 255, (float) a / 255);
    }

    public static void setShaderColor(Color color) {
        setShaderColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static void setShaderColor(double r, double g, double b, double a) {
        RenderSystem.setShaderColor((float) r, (float) g, (float) b, (float) a);
    }

    public static BufferBuilder start() {
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        setShaderColor(Color.WHITE);
        return Tessellator.getInstance().getBuffer();
    }

    public static void end(BufferBuilder bufferbuilder) {
        bufferbuilder.end();
        BufferRenderer.draw(bufferbuilder);
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
    }

    public static void rect(MatrixStack poseStack, float x, float y, float x2, float y2, float z, Color color) {
        BufferBuilder bufferbuilder = start();
        Matrix4f matrix = poseStack.peek().getPositionMatrix();
        bufferbuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferbuilder.vertex(matrix, x2, y, z).color(color.getRGB()).next();
        bufferbuilder.vertex(matrix, x, y, z).color(color.getRGB()).next();
        bufferbuilder.vertex(matrix, x, y2, z).color(color.getRGB()).next();
        bufferbuilder.vertex(matrix, x2, y2, z).color(color.getRGB()).next();
        end(bufferbuilder);
    }

    public static void rect(MatrixStack poseStack, float x, float y, float x2, float y2, Color color) {
        rect(poseStack, x, y, x2, y2, 0, color);
    }

    public static void rect(MatrixStack poseStack, int x, int y, int x2, int y2, Color color) {
        rect(poseStack, x, y, x2, y2, 0, color);
    }

    public static void rounded_rect(MatrixStack poseStack, int x, int y, int x2, int y2, int z, float radius, Color color) {
        int width = (int) (x2 - x - radius * 2);
        int heigth = (int) (y2 - y - radius * 2);
        float offset = 0.19f;
        Vector2f[] positions =
                new Vector2f[]{
                        new Vector2f(-offset, -offset), new Vector2f(-offset, offset), new Vector2f(offset, -offset), new Vector2f(offset, offset), new Vector2f(0, -offset), new Vector2f(0, offset), new Vector2f(-offset, 0), new Vector2f(offset, 0), new Vector2f(0, 0)};
        color = ColorHelper.withAlpha(color, (int) Math.ceil(((float) color.getAlpha() / positions.length) * 2));
        for (Vector2f o : positions) {
            float xo = x + o.getX();
            float yo = y + o.getY();
            BufferBuilder bufferbuilder = start();
            Matrix4f matrix = poseStack.peek().getPositionMatrix();
            bufferbuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
            bufferbuilder.vertex(matrix, xo + (float) width / 2 + radius, yo + (float) heigth / 2 + radius, z).color(color.getRGB()).next();
            for (int i = 360; i > 270; i--) {
                double angle = Math.toRadians(i);
                bufferbuilder.vertex(matrix, xo + width + (radius * (float) Math.cos(angle)) + radius, yo + (radius * (float) Math.sin(angle)) + radius, z).color(color.getRGB()).next();
            }

            for (int i = 270; i > 180; i--) {
                double angle = Math.toRadians(i);
                bufferbuilder.vertex(matrix, xo + (radius * (float) Math.cos(angle)) + radius, yo + (radius * (float) Math.sin(angle)) + radius, z).color(color.getRGB()).next();
            }

            for (int i = 180; i > 90; i--) {
                double angle = Math.toRadians(i);
                bufferbuilder.vertex(matrix, xo + (radius * (float) Math.cos(angle)) + radius, yo + heigth + (radius * (float) Math.sin(angle)) + radius, z).color(color.getRGB()).next();
            }

            for (int i = 90; i > 0; i--) {
                double angle = Math.toRadians(i);
                bufferbuilder.vertex(matrix, xo + width + (radius * (float) Math.cos(angle)) + radius, yo + heigth + (radius * (float) Math.sin(angle)) + radius, z).color(color.getRGB()).next();
            }
            bufferbuilder.vertex(matrix, xo + width + radius * 2, yo + radius, z).color(color.getRGB()).next();
            end(bufferbuilder);
        }
    }

    public static void rounded_rect(MatrixStack poseStack, int x, int y, int x2, int y2, float radius, Color color) {
        rounded_rect(poseStack, x, y, x2, y2, 0, radius, color);
    }

    public static void v_line(MatrixStack poseStack, int x, int y, int x2, int y2, int width, int z, Color color) {
        BufferBuilder bufferBuilder = start();
        Matrix4f matrix = poseStack.peek().getPositionMatrix();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, x + width, y, z).color(color.getRGB()).next();
        bufferBuilder.vertex(matrix, x, y, z).color(color.getRGB()).next();
        bufferBuilder.vertex(matrix, x2, y2, z).color(color.getRGB()).next();
        bufferBuilder.vertex(matrix, x2 + width, y2, z).color(color.getRGB()).next();
        end(bufferBuilder);
    }

    public static void v_line(MatrixStack poseStack, int x, int y, int x2, int y2, int width, Color color) {
        v_line(poseStack, x, y, x2, y2, width, 0, color);
    }

    public static void v_line(MatrixStack poseStack, float x, float y, float x2, float y2, int width, Color color) {
        v_line(poseStack, (int) x, (int) y, (int) x2, (int) y2, width, 0, color);
    }

    public static void textureFull(MatrixStack poseStack, int x, int y, float scaleX, float scaleY, Identifier texture) {
        poseStack.push();
        RenderSystem.enableTexture();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(770, 771);
        RenderSystem.setShaderTexture(0, texture);
        poseStack.scale(scaleX, scaleY, 1);
        Matrix4f matrix = poseStack.peek().getPositionMatrix();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(matrix, x, y + 256, 0).texture(0, 1).next();
        bufferBuilder.vertex(matrix, x + 256, y + 256, 0).texture(1, 1).next();
        bufferBuilder.vertex(matrix, x + 256, y, 0).texture(1, 0).next();
        bufferBuilder.vertex(matrix, x, y, 0).texture(0, 0).next();
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
        RenderSystem.disableTexture();
        RenderSystem.disableBlend();
        poseStack.pop();
    }
}
