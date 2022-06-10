package stanuwu.fragmentutils.mixin;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import stanuwu.fragmentutils.modules.Hud.HudModule;
import stanuwu.fragmentutils.modules.Modules;

@Mixin(InGameHud.class)
public class InGameHudRenderMixin {

    @Inject(at = @At("TAIL"), method = "render")
    private void afterHudRender(MatrixStack matrixStack, float tickDelta, CallbackInfo callbackInfo) {
        ((HudModule) Modules.getModule("hud")).render(matrixStack);
    }
}
