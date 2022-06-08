package stanuwu.fragmentutils.modules.PatchCrumbs;

import net.minecraft.entity.Entity;

public class TrackedEntity {
    Entity entity;
    boolean x;
    double origin;

    public TrackedEntity(Entity entity, boolean x, double origin) {
        this.entity = entity;
        this.x = x;
        this.origin = origin;
    }
}
