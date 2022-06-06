package stanuwu.fragmentutils.Events;

import net.minecraft.client.util.math.Vector3d;
import stanuwu.fragmentutils.modules.ExplosionBoxes.ExplosionBoxesModule;
import stanuwu.fragmentutils.modules.Modules;

public class ExplosionEvent {
    public static void handleExplosion(Vector3d pos) {
        //explosion boxes
        ((ExplosionBoxesModule) Modules.getModule("explosionboxes")).receiveExplosion(pos);
    }
}
