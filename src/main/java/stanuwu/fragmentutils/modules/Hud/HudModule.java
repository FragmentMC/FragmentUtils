package stanuwu.fragmentutils.modules.Hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;
import stanuwu.fragmentutils.modules.Hud.component.HudComponent;
import stanuwu.fragmentutils.modules.Module;

import java.awt.*;
import java.util.List;

public class HudModule extends Module {
    List<HudComponent> hudComponents;

    float primary_red;
    float primary_green;
    float primary_blue;

    float secondary_red;
    float secondary_green;
    float secondary_blue;

    String customTitle;
    String customText;

    public HudModule(boolean enabled, List<HudComponent> hudComponents, float primary_red, float primary_green, float primary_blue, float secondary_red, float secondary_green, float secondary_blue, String customTitle, String customText) {
        super(enabled, "hud", HudScreen.class, GLFW.GLFW_KEY_UNKNOWN);

        this.hudComponents = hudComponents;

        this.primary_red = primary_red;
        this.primary_green = primary_green;
        this.primary_blue = primary_blue;

        this.secondary_red = secondary_red;
        this.secondary_green = secondary_green;
        this.secondary_blue = secondary_blue;

        this.customText = customText;
        this.customTitle = customTitle;
    }

    public void render(MatrixStack poseStack) {
        if (getEnabled() && MinecraftClient.getInstance() != null && !MinecraftClient.getInstance().options.debugEnabled) {
            poseStack.push();
            float scale = (float) (2.0 / MinecraftClient.getInstance().getWindow().getScaleFactor());
            poseStack.scale(scale, scale, 1);
            for (HudComponent comp : hudComponents) {
                if (comp.isEnabled()) comp.render(poseStack);
            }
            poseStack.pop();
        }
    }

    public String getCustomTitle() {
        return customTitle;
    }

    public String getCustomText() {
        return customText;
    }

    public Color getPrimaryColor() {
        return new Color((int) primary_red, (int) primary_green, (int) primary_blue);
    }

    public Color getSecondaryColor() {
        return new Color((int) secondary_red, (int) secondary_green, (int) secondary_blue);
    }
}
