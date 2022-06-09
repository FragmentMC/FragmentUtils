package stanuwu.fragmentutils.modules.BreadCrumbs;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.TntEntity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;
import stanuwu.fragmentutils.modules.Module;
import stanuwu.fragmentutils.render.RenderHelper3d;

import java.awt.*;
import java.util.HashMap;

public class BreadCrumbsModule extends Module {
    float minTime = 1;
    float maxTime = 120;
    float time;
    boolean tnt;
    boolean sand;
    boolean triangle;
    float tnt_red;
    float tnt_green;
    float tnt_blue;
    float tnt_alpha;
    float sand_red;
    float sand_green;
    float sand_blue;
    float sand_alpha;

    HashMap<String, BreadCrumbLine> crumbs = new HashMap<>();
    HashMap<Integer, Vec3d> prevPos = new HashMap<>();

    public BreadCrumbsModule(boolean enabled, float time, boolean tnt, boolean sand, boolean triangle, float tnt_red, float tnt_green, float tnt_blue, float tnt_alpha, float sand_red, float sand_green, float sand_blue, float sand_alpha) {
        super(enabled, "breadcrumbs", BreadCrumbsScreen.class, GLFW.GLFW_KEY_UNKNOWN);
        this.time = time;
        this.tnt = tnt;
        this.sand = sand;
        this.triangle = triangle;
        this.tnt_red = tnt_red;
        this.tnt_green = tnt_green;
        this.tnt_blue = tnt_blue;
        this.tnt_alpha = tnt_alpha;
        this.sand_red = sand_red;
        this.sand_green = sand_green;
        this.sand_blue = sand_blue;
        this.sand_alpha = sand_alpha;

        WorldRenderEvents.LAST.register(this::lastWorldRender);
        ClientEntityEvents.ENTITY_LOAD.register(this::entityLoad);
        ClientEntityEvents.ENTITY_UNLOAD.register(this::entityUnload);
        ClientTickEvents.END_CLIENT_TICK.register(this::endTick);

    }

    private void entityLoad(Entity entity, ClientWorld clientWorld) {
        if (!getEnabled()) return;

        if (entity instanceof TntEntity || entity instanceof FallingBlockEntity) {
            prevPos.put(entity.getId(), entity.getPos());
        }
    }

    private void entityUnload(Entity entity, ClientWorld clientWorld) {
        if (!getEnabled()) return;

        if (entity instanceof TntEntity || entity instanceof FallingBlockEntity) {
            prevPos.remove(entity.getId());
        }
    }

    private void lastWorldRender(WorldRenderContext worldRenderContext) {
        if (!getEnabled()) return;

        BufferBuilder bufferBuilder = RenderHelper3d.startLines();
        for (BreadCrumbLine breadCrumbLine : crumbs.values().stream().toList()) {
            if (breadCrumbLine != null) {
                if (breadCrumbLine.age <= 20 * time) {
                    breadCrumbLine.render(bufferBuilder);
                } else {
                    crumbs.remove(breadCrumbLine.getKey());
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
                if (entity instanceof TntEntity || entity instanceof FallingBlockEntity) {
                    if (!prevPos.containsKey(entity.getId())) return;

                    boolean tnt = entity instanceof TntEntity;

                    if (tnt && !this.tnt) return;
                    if (!tnt && !this.sand) return;

                    Color color = tnt ?
                            new Color((int) tnt_red, (int) tnt_green, (int) tnt_blue, (int) tnt_alpha) :
                            new Color((int) sand_red, (int) sand_green, (int) sand_blue, (int) sand_alpha);
                    Vec3d pos = entity.getPos();
                    Vec3d prev = prevPos.get(entity.getId());
                    Vec3d delta = pos.subtract(prev);

                    if (triangle) {
                        if (delta.y != 0) {
                            addLine(new BreadCrumbLine(prev.x, prev.y, prev.z, prev.x, pos.y, prev.z, 0, color));
                        }
                        if (delta.x != 0 && delta.y != 0) {
                            boolean sure = false;
                            boolean xSmaller = false;
                            if (!entity.horizontalCollision) {
                                xSmaller = delta.x < delta.y;
                                sure = true;
                            }

                            if (sure) {
                                if (xSmaller) {
                                    addLine(new BreadCrumbLine(prev.x, pos.y, prev.z, pos.x, pos.y, prev.z, 0, color));
                                    addLine(new BreadCrumbLine(pos.x, pos.y, prev.z, pos.x, pos.y, pos.z, 0, color));
                                } else {
                                    addLine(new BreadCrumbLine(prev.x, pos.y, prev.z, prev.x, pos.y, pos.z, 0, color));
                                    addLine(new BreadCrumbLine(prev.x, pos.y, pos.z, pos.x, pos.y, pos.z, 0, color));
                                }
                            } else {
                                addLine(new BreadCrumbLine(prev.x, pos.y, prev.z, pos.x, pos.y, pos.z, 0, color));
                            }
                        } else if (delta.x != 0 && delta.z == 0) {
                            addLine(new BreadCrumbLine(prev.x, pos.y, prev.z, pos.x, pos.y, prev.z, 0, color));
                        } else if (delta.x == 0 && delta.z != 0) {
                            addLine(new BreadCrumbLine(prev.x, pos.y, prev.z, prev.x, pos.y, pos.z, 0, color));
                            if (entity instanceof TntEntity && ((TntEntity) entity).getFuse() == 1) {
                                entity.move(MovementType.SELF, new Vec3d(delta.x, delta.y, delta.z));
                                double y = entity.getY() + (entity.getVelocity().y == 0 ? 0 : -0.04);
                                addLine(new BreadCrumbLine(pos.x, pos.y, pos.z, pos.x, y, pos.z, -1, color));
                                addLine(new BreadCrumbLine(pos.x, y, pos.z, entity.getX(), y, entity.getZ(), -1, color));
                            }
                        }
                    } else {
                        addLine(new BreadCrumbLine(prev.x, prev.y, prev.z, pos.x, pos.y, pos.z, 0, color));
                        if (entity instanceof TntEntity && ((TntEntity) entity).getFuse() == 1) {
                            entity.move(MovementType.SELF, new Vec3d(delta.x, delta.y, delta.z));
                            addLine(new BreadCrumbLine(pos.x, pos.y, pos.z, entity.getX(), entity.getY() + (entity.getVelocity().y == 0 ? 0 : -0.04), entity.getZ(), -1, color));
                        }
                    }

                    if (prevPos.containsKey(entity.getId())) {
                        prevPos.replace(entity.getId(), new Vec3d(entity.getX(), entity.getY(), entity.getZ()));
                    } else {
                        prevPos.put(entity.getId(), new Vec3d(entity.getX(), entity.getY(), entity.getZ()));
                    }
                }
            }));
        }

        crumbs.values().stream().toList().forEach((crumb) -> crumb.age++);
    }

    private void addLine(BreadCrumbLine line) {
        line.y1 += 0.49;
        line.y2 += 0.49;
        String key = line.getKey();
        if (crumbs.putIfAbsent(key, line) != null) {
            crumbs.get(key).age = 0;
        }
    }
}
