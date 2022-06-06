package stanuwu.fragmentutils.modules.ExplosionBoxes;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.util.math.Vector3d;
import org.lwjgl.glfw.GLFW;
import stanuwu.fragmentutils.modules.Module;
import stanuwu.fragmentutils.render.RenderHelper3d;

import java.awt.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ExplosionBoxesModule extends Module {
    HashMap<String, ExplosionBox> explosionBoxes = new HashMap<>() {
    };

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
    }

    private void endClientTick(MinecraftClient minecraftClient) {
        if (!getEnabled()) return;

        Set<String> toRemove = new HashSet<String>() {
        };
        explosionBoxes.forEach((key, box) -> {
            box.tick();
            if (box.age > time * 20) {
                toRemove.add(key);
            }
        });
        toRemove.forEach((key) -> explosionBoxes.remove(key));
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
    }

    public void receiveExplosion(Vector3d vector3d) {
        if (!getEnabled()) return;

        String key = vec3dToString(vector3d);

        if (explosionBoxes.containsKey(key)) {
            if (explosionBoxes.get(key).age != 0) {
                explosionBoxes.get(key).age = 0;
            }
        } else {
            explosionBoxes.put(key, new ExplosionBox(vector3d.x, vector3d.y, vector3d.z));
        }

    }

    String vec3dToString(Vector3d vec3d) {
        DecimalFormat decimalFormat = new DecimalFormat("#.######");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        return new String(new StringBuilder().append(decimalFormat.format(vec3d.x)).append(decimalFormat.format(vec3d.y)).append(decimalFormat.format(vec3d.z)));
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
