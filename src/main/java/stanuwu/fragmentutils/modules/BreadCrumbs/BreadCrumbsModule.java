package stanuwu.fragmentutils.modules.BreadCrumbs;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;
import stanuwu.fragmentutils.modules.Module;
import stanuwu.fragmentutils.render.RenderHelper3d;

import java.awt.*;
import java.util.HashSet;

public class BreadCrumbsModule extends Module {
    float minTime = 1;
    float maxTime = 120;
    float time;
    boolean tnt;
    boolean sand;
    float tnt_red;
    float tnt_green;
    float tnt_blue;
    float tnt_alpha;
    float sand_red;
    float sand_green;
    float sand_blue;
    float sand_alpha;

    HashSet<BreadCrumbLine> crumbs = new HashSet<>();

    public BreadCrumbsModule(boolean enabled, float time, boolean tnt, boolean sand, float tnt_red, float tnt_green, float tnt_blue, float tnt_alpha, float sand_red, float sand_green, float sand_blue, float sand_alpha) {
        super(enabled, "breadcrumbs", BreadCrumbsScreen.class, GLFW.GLFW_KEY_UNKNOWN);
        this.time = time;
        this.tnt = tnt;
        this.sand = sand;
        this.tnt_red = tnt_red;
        this.tnt_green = tnt_green;
        this.tnt_blue = tnt_blue;
        this.tnt_alpha = tnt_alpha;
        this.sand_red = sand_red;
        this.sand_green = sand_green;
        this.sand_blue = sand_blue;
        this.sand_alpha = sand_alpha;

        ClientTickEvents.END_CLIENT_TICK.register(this::endTick);
        WorldRenderEvents.LAST.register(this::lastWorldRender);
    }

    private void lastWorldRender(WorldRenderContext worldRenderContext) {
        if (!getEnabled()) return;

        BufferBuilder bufferBuilder = RenderHelper3d.startLines();
        for (BreadCrumbLine breadCrumbLine : crumbs.stream().toList()) {
            if (breadCrumbLine != null) {
                if (breadCrumbLine.age <= 20 * time) {
                    breadCrumbLine.render(bufferBuilder);
                } else {
                    crumbs.remove(breadCrumbLine);
                }
            }
        }
        RenderHelper3d.end(bufferBuilder, worldRenderContext);
    }

    private void endTick(MinecraftClient minecraftClient) {
        if (!getEnabled()) return;

        ClientWorld clientWorld = minecraftClient.world;
        if (clientWorld != null) {
            clientWorld.getEntities().forEach((entity -> {
                if (entity instanceof TntEntity && tnt) {
                    Color color = new Color((int) tnt_red, (int) tnt_green, (int) tnt_blue, (int) tnt_alpha);
                    Vec3d vel = entity.getVelocity();
                    double x1 = entity.getX();
                    double y1 = entity.getY();
                    double z1 = entity.getZ();
                    double x2 = x1 + vel.getX();
                    double y2 = y1 + vel.getY() - (vel.getY() == 0.0 ? 0.0 : 0.04d);
                    double z2 = z1 + vel.getZ();
                    boolean xSmaller = Math.abs(entity.getVelocity().getX()) < Math.abs(entity.getVelocity().getZ());
                    if (vel.getY() != 0.0) {
                        crumbs.add(new BreadCrumbLine(x1, y1, z1, x1, y2, z1, 0, color));
                    }
                    if (xSmaller && vel.getZ() != 0.0) {
                        crumbs.add(new BreadCrumbLine(x1, y2, z1, x1, y2, z2, 0, color));
                        if (entity.getVelocity().getX() != 0.0) {
                            crumbs.add(new BreadCrumbLine(x1, y2, z2, x2, y2, z2, 0, color));
                        }
                    }
                    if (vel.getX() != 0.0) {
                        crumbs.add(new BreadCrumbLine(x1, y2, z1, x2, y2, z1, 0, color));
                        if (!xSmaller && vel.getX() != 0.0) {
                            crumbs.add(new BreadCrumbLine(x2, y2, z1, x2, y2, z2, 0, color));
                        }
                    }
                } else if (entity instanceof FallingBlockEntity && sand) {
                    Color color = new Color((int) sand_red, (int) sand_green, (int) sand_blue, (int) sand_alpha);
                    crumbs.add(new BreadCrumbLine(entity.getX(), entity.getY(), entity.getZ(), entity.getX(), entity.getY() + entity.getVelocity().getY() - 0.04d, entity.getZ(), 0, color));
                }
            }));
        }

        crumbs.stream().toList().forEach((crumb) -> crumb.age++);

        ClientPlayerEntity player = minecraftClient.player;
        if (player != null) {
            player.sendMessage(new TranslatableText(Integer.toString(crumbs.size())), true);
        }
    }
}
