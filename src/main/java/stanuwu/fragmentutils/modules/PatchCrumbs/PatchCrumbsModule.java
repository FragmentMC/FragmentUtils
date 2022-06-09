package stanuwu.fragmentutils.modules.PatchCrumbs;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;
import stanuwu.fragmentutils.Utils.PlayerUtils;
import stanuwu.fragmentutils.modules.Module;
import stanuwu.fragmentutils.render.RenderHelper3d;

import java.awt.*;
import java.util.HashMap;

public class PatchCrumbsModule extends Module {
    boolean path;
    boolean path_sideways;
    boolean tracers;
    boolean sand;
    float minOffset = -10;
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

    double dist_thresh = 32;
    double vel_thresh = 1;
    int fuse_thresh = 70;
    int new_crumb_delay = 40;
    double alignment_offset = 0.49000000953674316;
    HashMap<Integer, Vec3d> prevPos = new HashMap<>();
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
        ClientEntityEvents.ENTITY_UNLOAD.register(this::entityUnload);
    }

    private void lastWorldRender(WorldRenderContext worldRenderContext) {
        if (!getEnabled()) return;

        if (crumbs_pos != null && crumbs_age <= time * 20) {
            BufferBuilder bufferBuilder = RenderHelper3d.startLines();
            float dist = MinecraftClient.getInstance().options.viewDistance * 16;
            Color color = new Color((int) red, (int) green, (int) blue, (int) alpha);
            if (path) {
                RenderHelper3d.renderInfiniteQuadLines(bufferBuilder, crumbs_x, crumbs_pos.x - size / 2, Math.round(crumbs_pos.y + y_offset) + size / 2 + 0.5, crumbs_pos.z + size / 2, size, dist, color);
            }
            if (path_sideways) {
                RenderHelper3d.renderInfiniteQuadLines(bufferBuilder, !crumbs_x, crumbs_pos.x - size / 2, Math.round(crumbs_pos.y + y_offset) + size / 2 + 0.5, crumbs_pos.z - size / 2 + (crumbs_x ? 0 : 1), size, dist, color);
                RenderHelper3d.renderCubeOutline(bufferBuilder, crumbs_pos.x - size / 2, Math.round(crumbs_pos.y + y_offset) + (1 - size / 2) - 0.5, crumbs_pos.z - size / 2, size, color);
            }
            if (tracers) {
                ClientPlayerEntity player = MinecraftClient.getInstance().player;
                if (player != null) {
                    Vec3d screenCenter = PlayerUtils.getInterpolatedPosition().add(0, player.getEyeY() - player.getY(), 0).add(player.getRotationVecClient());
                    RenderHelper3d.renderLine(bufferBuilder, crumbs_pos.x, Math.round(crumbs_pos.y + y_offset) + size / 2 - 0.44875f, crumbs_pos.z, screenCenter.x, screenCenter.y, screenCenter.z, color);
                }
            }
            RenderHelper3d.end(bufferBuilder, worldRenderContext);
        }
    }

    private void entityUnload(Entity entity, ClientWorld clientWorld) {
        if (!getEnabled()) return;

        if (entity instanceof TntEntity || entity instanceof FallingBlockEntity) {
            prevPos.remove(entity.getId());
        }
    }

    private void setCrumbs(Vec3d pos, boolean x) {
        crumbs_pos = pos;
        crumbs_age = 0;
        crumbs_x = x;
    }

    private void endTick(MinecraftClient minecraftClient) {
        if (!getEnabled()) return;
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.world != null)
            for (Entity entity : client.world.getEntities()) {
                if (entity instanceof TntEntity || (sand && entity instanceof FallingBlockEntity)) {
                    Vec3d prev = prevPos.getOrDefault(entity.getId(), null);
                    if (crumbs_age > new_crumb_delay && entity.getVelocity().x < vel_thresh && entity.getVelocity().y < vel_thresh) {
                        if (prev != null) {
                            if (Math.abs(entity.getX() - prev.x) > dist_thresh) {
                                setCrumbs(entity.getPos(), true);
                            } else if (Math.abs(entity.getZ() - prev.z) > dist_thresh) {
                                setCrumbs(entity.getPos(), false);
                            }
                        } else {
                            if (entity.isSubmergedInWater() && (entity instanceof TntEntity ? ((TntEntity) entity).getFuse() < fuse_thresh : false)) {
                                if (Math.abs(Math.floor(entity.getX()) - entity.getX()) == alignment_offset) {
                                    setCrumbs(new Vec3d(entity.lastRenderX, entity.lastRenderY, entity.lastRenderZ), true);
                                } else if (Math.abs(Math.floor(entity.getZ()) - entity.getZ()) == alignment_offset) {
                                    setCrumbs(new Vec3d(entity.lastRenderX, entity.lastRenderY, entity.lastRenderZ), false);
                                }
                            }
                        }
                    }

                    if (prevPos.containsKey(entity.getId())) {
                        prevPos.replace(entity.getId(), new Vec3d(entity.getX(), entity.getY(), entity.getZ()));
                    } else {
                        prevPos.put(entity.getId(), new Vec3d(entity.getX(), entity.getY(), entity.getZ()));
                    }
                }
            }

        crumbs_age++;
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
