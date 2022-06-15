package stanuwu.fragmentutils.modules.ExplosionBoxes;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import org.lwjgl.glfw.GLFW;
import stanuwu.fragmentutils.events.EventHandler;
import stanuwu.fragmentutils.events.EventType;
import stanuwu.fragmentutils.events.events.Event;
import stanuwu.fragmentutils.events.events.ReceivePacketEvent;
import stanuwu.fragmentutils.modules.Module;
import stanuwu.fragmentutils.render.RenderHelper3d;
import stanuwu.fragmentutils.utils.DoubleHelper;

import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;

public class ExplosionBoxesModule extends Module {
    private ConcurrentHashMap<Long, ExplosionBox> explosionBoxes = new ConcurrentHashMap<>();

    float minSize = 0.1f;
    float maxSize = 1;
    float size;
    float minTime = 1;
    float maxTime = 60;
    float time;
    boolean blockPosition;
    boolean highlightOrigin;
    float red;
    float green;
    float blue;
    float alpha;

    public ExplosionBoxesModule(boolean enabled, float size, float time, boolean blockPosition, boolean highlightOrigin, Color color) {
        super(enabled,
                "explosionboxes",
                ExplosionBoxesScreen.class,
                GLFW.GLFW_KEY_UNKNOWN);
        this.size = size;
        this.time = time;
        this.blockPosition = blockPosition;
        this.highlightOrigin = highlightOrigin;
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        this.alpha = color.getAlpha();

        WorldRenderEvents.LAST.register(this::lastWorldRender);
        ClientTickEvents.END_CLIENT_TICK.register(this::endClientTick);
        EventHandler.getInstance().register(EventType.ReceivePacket, this::receiveExplosion);
    }

    private void endClientTick(MinecraftClient minecraftClient) {
        if (!getEnabled()) return;

        explosionBoxes.values().forEach(ExplosionBox::tick);
    }

    private void lastWorldRender(WorldRenderContext context) {
        if (!getEnabled()) return;

        BufferBuilder bufferBuilder = RenderHelper3d.startQuads();
        for (ExplosionBox box : explosionBoxes.values()) {
            if (box != null) {
                RenderHelper3d.renderCubeArea(bufferBuilder, box.x - size / 2, box.y - size / 2 + (blockPosition ? 0.44875f : 0), box.z - size / 2, size, new Color((int) red, (int) green, (int) blue, (int) alpha));
                if (highlightOrigin) {
                    RenderHelper3d.renderCubeArea(bufferBuilder, box.x - 0.025f, box.y - 0.025f, box.z - 0.025f, 0.05f, Color.CYAN);
                }
            }
        }
        RenderHelper3d.end(bufferBuilder, context);
        bufferBuilder = RenderHelper3d.startLines();
        for (ExplosionBox box : explosionBoxes.values()) {
            if (box != null) {
                RenderHelper3d.renderCubeOutline(bufferBuilder, box.x - size / 2, box.y - size / 2 + (blockPosition ? 0.44875f : 0), box.z - size / 2, size, new Color((int) red, (int) green, (int) blue, 255));
            }
        }
        RenderHelper3d.end(bufferBuilder, context);
        explosionBoxes.keySet().removeIf(key -> explosionBoxes.get(key).age > time * 20);
    }

    public void receiveExplosion(Event e) {
        if (!getEnabled()) return;

        ReceivePacketEvent event = (ReceivePacketEvent) e;
        if (event.getPacket() instanceof ExplosionS2CPacket ePacket) {
            long key = xyzToKey(ePacket.getX(), ePacket.getY(), ePacket.getZ());
            if (explosionBoxes.putIfAbsent(key, new ExplosionBox(ePacket.getX(), ePacket.getY(), ePacket.getZ())) != null) {
                explosionBoxes.get(key).age = 0;
            }
        }
    }

    private long xyzToKey(double x, double y, double z) {
        return DoubleHelper.makeKeyElement(x) ^
                DoubleHelper.makeKeyElement(y) ^
                DoubleHelper.makeKeyElement(z);
    }

    public float getSize() {
        return size;
    }

    public float getTime() {
        return time;
    }

    public boolean getBlockPosition() {
        return blockPosition;
    }

    public boolean getHighlightOrigin() {
        return highlightOrigin;
    }

    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }

    public float getAlpha() {
        return alpha;
    }
}
