package stanuwu.fragmentutils.mixin;

import net.minecraft.resource.AbstractFileResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFileResourcePack.class)
public class AbstractFileResourcePackMixin {
    @Inject(method = "contains", at = @At(value = "HEAD"), cancellable = true)
    private void preventArmorShaderLoading(ResourceType type, Identifier id, CallbackInfoReturnable<Boolean> cir) {
        if (!((AbstractFileResourcePack) (Object) this).getName().contains(".jar") && id.getPath().contains("rendertype_armor_cutout_no_cull")) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
