package stanuwu.fragmentutils.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import stanuwu.fragmentutils.events.EventHandler;
import stanuwu.fragmentutils.events.EventType;
import stanuwu.fragmentutils.events.events.GetCapeTextureEvent;

@Mixin(AbstractClientPlayerEntity.class)
public class PlayerGetCapeTextureMixin {
    @Inject(method = "getCapeTexture", at = @At("HEAD"), cancellable = true)
    public void makeCapeTextureEvent(CallbackInfoReturnable<Identifier> cir) {
        GetCapeTextureEvent event = new GetCapeTextureEvent(((Entity) (Object) this).getUuid(), null);
        EventHandler.getInstance().fire(EventType.GetCapeTexture, event);
        if (event.isCancelled()) {
            cir.cancel();
        } else if (event.getTexture() != null) {
            cir.setReturnValue(event.getTexture());
            cir.cancel();
        }
    }
}
