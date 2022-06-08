package stanuwu.fragmentutils.modules.PatchCrumbs;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;
import stanuwu.fragmentutils.modules.Module;
import stanuwu.fragmentutils.render.RenderHelper3d;

import java.awt.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PatchCrumbsModule extends Module {
    boolean path;
    boolean path_sideways;
    boolean tracers;
    boolean sand;
    float minOffset = 0;
    float maxOffset = 10;
    float y_offset;
    float minTime = 1;
    float maxTime = 60;
    float time;
    float minSize = 0.1f;
    float maxSize = 1;
    float size;
    float red;
    float green;
    float blue;
    float alpha;

    double vel_thresh = 50;
    HashMap<String, TrackedEntity> tracked = new HashMap<>();
    Vec3d crumbs_pos = null;
    boolean crumbs_x = true;
    int crumbs_age = 0;

    public PatchCrumbsModule(boolean enabled, boolean path, boolean path_sideways, boolean tracers, boolean sand, float y_offset, float size, float time, float red, float green, float blue, float alpha) {
        super(enabled,
                "patchcrumbs",
                PatchCrumbsScreen.class,
                GLFW.GLFW_KEY_UNKNOWN);
        this.path = path;
        this.path_sideways = path_sideways;
        this.tracers = tracers;
        this.sand = sand;
        this.y_offset = y_offset;
        this.size = size;
        this.time = time;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;

        ClientTickEvents.END_CLIENT_TICK.register(this::endTick);
        WorldRenderEvents.LAST.register(this::lastWorldRender);
    }

    private void lastWorldRender(WorldRenderContext worldRenderContext) {
        if (!getEnabled()) return;

        if (crumbs_pos != null && crumbs_age <= time * 20) {
            BufferBuilder bufferBuilder = RenderHelper3d.startLines();
            RenderHelper3d.renderCubeOutline(bufferBuilder, crumbs_pos.x - size / 2, crumbs_pos.y - size / 2, crumbs_pos.z - size / 2, size, new Color(red, green, blue, alpha));
            RenderHelper3d.end(bufferBuilder, worldRenderContext);
        }
    }

    private void endTick(MinecraftClient minecraftClient) {
        if (!getEnabled()) return;
        MinecraftClient client = MinecraftClient.getInstance();

        int entities = 0;

        if (client.world != null)
            for (Entity entity : client.world.getEntities()) {
                if (entity instanceof TntEntity || (sand && entity instanceof FallingBlockEntity)) entities++;

                if (entity.getVelocity().x > vel_thresh) {
                    String key = entityToKey(entity);
                    tracked.putIfAbsent(key, new TrackedEntity(entity, true, entity.getX()));
                } else if (entity.getVelocity().y > vel_thresh) {
                    String key = entityToKey(entity);
                    tracked.putIfAbsent(key, new TrackedEntity(entity, false, entity.getZ()));
                }
            }

        crumbs_age++;

        List<String> expired = new ArrayList<>();
        tracked.forEach((key, trackedEntity) -> {
            if (trackedEntity.entity == null) {
                expired.add(key);
            } else if ((trackedEntity.x ? trackedEntity.entity.getVelocity().x : trackedEntity.entity.getVelocity().z) < 1) {
                crumbs_pos = trackedEntity.entity.getPos();
                crumbs_age = 0;
                crumbs_x = trackedEntity.x;
            }
        });

        expired.forEach(tracked::remove);

        if (client.player != null) {
            client.player.sendMessage(new TranslatableText(Integer.toString(entities)), true);
        }
    }

    String entityToKey(Entity entity) {
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        return new String(new StringBuilder()
                .append(decimalFormat.format(entity.getX()))
                .append(decimalFormat.format(entity.getY()))
                .append(decimalFormat.format(entity.getZ()))
                .append(decimalFormat.format(entity.getVelocity().x))
                .append(decimalFormat.format(entity.getVelocity().y))
                .append(decimalFormat.format(entity.getVelocity().z))
        );
    }

    public boolean getPath() {
        return path;
    }

    public boolean getPath_sideways() {
        return path_sideways;
    }

    public boolean getTracers() {
        return tracers;
    }

    public boolean getSand() {
        return sand;
    }

    public float getY_offset() {
        return y_offset;
    }

    public float getTime() {
        return time;
    }

    public float getSize() {
        return size;
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
