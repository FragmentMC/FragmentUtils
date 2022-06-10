package stanuwu.fragmentutils.Utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class PlayerHelper {
    public static Vec3d getInterpolatedPosition() {
        MinecraftClient mc = MinecraftClient.getInstance();
        ClientPlayerEntity player = mc.player;
        final double interpolated_x = mc.getCameraEntity().lastRenderX + (player.getX() - mc.getCameraEntity().lastRenderX) * mc.getTickDelta();
        final double interpolated_y = mc.getCameraEntity().lastRenderY + (player.getY() - mc.getCameraEntity().lastRenderY) * mc.getTickDelta();
        final double interpolated_z = mc.getCameraEntity().lastRenderZ + (player.getZ() - mc.getCameraEntity().lastRenderZ) * mc.getTickDelta();
        return new Vec3d(interpolated_x, interpolated_y, interpolated_z);
    }
}
