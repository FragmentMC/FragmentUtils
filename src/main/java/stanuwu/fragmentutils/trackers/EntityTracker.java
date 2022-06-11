package stanuwu.fragmentutils.trackers;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.TntEntity;

public class EntityTracker {
    private static int tnt = 0;
    private static int sand = 0;

    public static void init() {
        ClientEntityEvents.ENTITY_LOAD.register(EntityTracker::onEntityLoad);
        ClientEntityEvents.ENTITY_UNLOAD.register(EntityTracker::onEntityUnload);
    }

    private static void onEntityUnload(Entity entity, ClientWorld clientWorld) {
        if (entity instanceof TntEntity) {
            tnt--;
        } else if (entity instanceof FallingBlockEntity) {
            sand--;
        }
    }

    private static void onEntityLoad(Entity entity, ClientWorld clientWorld) {
        if (entity instanceof TntEntity) {
            tnt++;
        } else if (entity instanceof FallingBlockEntity) {
            sand++;
        }
    }

    public static int getTnt() {
        return tnt;
    }

    public static int getSand() {
        return sand;
    }
}
