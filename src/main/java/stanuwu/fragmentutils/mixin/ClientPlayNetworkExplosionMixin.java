package stanuwu.fragmentutils.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import stanuwu.fragmentutils.Events.ExplosionEvent;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkExplosionMixin {

    @Inject(at = @At("HEAD"), method = "onExplosion")
    private void makeExplosionEvent(ExplosionS2CPacket packet, CallbackInfo callbackInfo) {
        ExplosionEvent.handleExplosion(new Vector3d(packet.getX(), packet.getY(), packet.getZ()));
    }
}
