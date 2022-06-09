package stanuwu.fragmentutils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderHelper3d {
    public static BufferBuilder startLines() {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        return bufferBuilder;
    }

    public static BufferBuilder startQuads() {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.disableCull();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        return bufferBuilder;
    }

    public static void end(BufferBuilder bufferBuilder, WorldRenderContext context) {
        bufferBuilder.end();
        VertexBuffer vertexBuffer = new VertexBuffer();
        vertexBuffer.upload(bufferBuilder);
        Camera camera = context.camera();
        Vec3d cameraPos = camera.getPos();

        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();

        MatrixStack poseStack = RenderSystem.getModelViewStack();
        poseStack.push();
        poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        RenderSystem.applyModelViewMatrix();

        RenderSystem.depthFunc(GL11.GL_ALWAYS);
        vertexBuffer.setShader(poseStack.peek().getPositionMatrix(), context.projectionMatrix().copy(), GameRenderer.getPositionColorShader());
        RenderSystem.depthFunc(GL11.GL_LEQUAL);

        poseStack.pop();
        RenderSystem.applyModelViewMatrix();
        RenderSystem.enableCull();
    }

    public static void renderLine(BufferBuilder bufferBuilder, double x1, double y1, double z1, double x2, double y2, double z2, Color color) {
        final float red = color.getRed() / 255f;
        final float green = color.getGreen() / 255f;
        final float blue = color.getBlue() / 255f;
        final float alpha = color.getAlpha() / 255f;

        bufferBuilder.vertex(x1, y1, z1).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x2, y2, z2).color(red, green, blue, alpha).next();
    }

    public static void renderInfiniteQuadLines(BufferBuilder bufferBuilder, boolean x_axis, double x, double y, double z, float size, float dist, Color color) {
        final float red = color.getRed() / 255f;
        final float green = color.getGreen() / 255f;
        final float blue = color.getBlue() / 255f;
        final float alpha = color.getAlpha() / 255f;

        float dist2 = 0;
        float size2 = 0;
        if (!x_axis) {
            dist2 = dist;
            dist = 0;
            size2 = size;
            size = 0;
        }

        bufferBuilder.vertex(x - dist, y, z - dist2).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x + dist, y, z + dist2).color(red, green, blue, alpha).next();

        bufferBuilder.vertex(x + size2 - dist, y, z - size - dist2).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x + size2 + dist, y, z - size + dist2).color(red, green, blue, alpha).next();

        bufferBuilder.vertex(x - dist, y - size - size2, z - dist2).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x + dist, y - size - size2, z + dist2).color(red, green, blue, alpha).next();

        bufferBuilder.vertex(x + size2 - dist, y - size - size2, z - size - dist2).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x + size2 + dist, y - size - size2, z - size + dist2).color(red, green, blue, alpha).next();
    }

    public static void renderCubeOutline(BufferBuilder bufferBuilder, double x, double y, double z, float size, Color color) {
        final float red = color.getRed() / 255f;
        final float green = color.getGreen() / 255f;
        final float blue = color.getBlue() / 255f;
        final float alpha = color.getAlpha() / 255f;

        //bottom
        bufferBuilder.vertex(x, y, z).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x, y, z + size).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x, y, z + size).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x + size, y, z + size).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x + size, y, z + size).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x + size, y, z).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x + size, y, z).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x, y, z).color(red, green, blue, alpha).next();

        //top
        bufferBuilder.vertex(x, y + size, z).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x, y + size, z + size).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x, y + size, z + size).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x + size, y + size, z + size).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x + size, y + size, z + size).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x + size, y + size, z).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x + size, y + size, z).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x, y + size, z).color(red, green, blue, alpha).next();

        //edge 1
        bufferBuilder.vertex(x, y, z).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x, y + size, z).color(red, green, blue, alpha).next();

        //edge 2
        bufferBuilder.vertex(x + size, y, z).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x + size, y + size, z).color(red, green, blue, alpha).next();

        //edge 3
        bufferBuilder.vertex(x, y, z + size).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x, y + size, z + size).color(red, green, blue, alpha).next();

        //edge 4
        bufferBuilder.vertex(x + size, y, z + size).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x + size, y + size, z + size).color(red, green, blue, alpha).next();
    }

    public static void renderCubeArea(BufferBuilder bufferBuilder, double x, double y, double z, float size, Color color) {
        float red = color.getRed() / 255f;
        float green = color.getGreen() / 255f;
        float blue = color.getBlue() / 255f;
        float alpha = color.getAlpha() / 255f;

        //bottom
        bufferBuilder.vertex(x, y, z).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x + size, y, z).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x + size, y, z + size).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x, y, z + size).color(red, green, blue, alpha).next();

        //top
        bufferBuilder.vertex(x, y + size, z).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x, y + size, z + size).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x + size, y + size, z + size).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x + size, y + size, z).color(red, green, blue, alpha).next();

        //side 1
        bufferBuilder.vertex(x, y, z).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x, y + size, z).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x + size, y + size, z).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x + size, y, z).color(red, green, blue, alpha).next();

        //side 2
        bufferBuilder.vertex(x, y, z + size).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x + size, y, z + size).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x + size, y + size, z + size).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x, y + size, z + size).color(red, green, blue, alpha).next();
        //side 3
        bufferBuilder.vertex(x + size, y, z).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x + size, y + size, z).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x + size, y + size, z + size).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x + size, y, z + size).color(red, green, blue, alpha).next();

        //side 4
        bufferBuilder.vertex(x, y, z).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x, y, z + size).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x, y + size, z + size).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x, y + size, z).color(red, green, blue, alpha).next();
    }
}
