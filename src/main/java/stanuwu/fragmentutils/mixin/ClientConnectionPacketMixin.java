package stanuwu.fragmentutils.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.PacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import stanuwu.fragmentutils.events.EventHandler;
import stanuwu.fragmentutils.events.EventType;
import stanuwu.fragmentutils.events.events.ReceivePacketEvent;

@Mixin(ClientConnection.class)
public class ClientConnectionPacketMixin {
    @Inject(at = @At("HEAD"), method = "handlePacket", cancellable = true)
    private static void firePacketEvent(Packet packet, PacketListener listener, CallbackInfo ci) {
        ReceivePacketEvent event = new ReceivePacketEvent(packet);
        EventHandler.getInstance().fire(EventType.ReceivePacket, event);
        if (event.isCancelled()) ci.cancel();
    }
}