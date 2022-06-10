package stanuwu.fragmentutils.modules.Hud;

import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;
import stanuwu.fragmentutils.modules.Hud.component.HudComponent;
import stanuwu.fragmentutils.modules.Module;

import java.util.List;

public class HudModule extends Module {
    List<HudComponent> hudComponents;

    public HudModule(boolean enabled, List<HudComponent> hudComponents) {
        super(enabled, "hud", HudScreen.class, GLFW.GLFW_KEY_UNKNOWN);

        this.hudComponents = hudComponents;
    }

    public void render(MatrixStack poseStack) {
        if (getEnabled())
            hudComponents.forEach((comp) -> {
                if (comp.isEnabled()) comp.render(poseStack);
            });
    }
}
