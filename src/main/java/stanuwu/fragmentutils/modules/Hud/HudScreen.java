package stanuwu.fragmentutils.modules.Hud;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import stanuwu.fragmentutils.gui.SubScreen;
import stanuwu.fragmentutils.gui.Theme;
import stanuwu.fragmentutils.modules.Modules;

public class HudScreen extends SubScreen {
    HudModule module = (HudModule) Modules.getModule("hud");


    public HudScreen(Text title) {
        super(title);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            module.hudComponents.forEach((comp) -> comp.drag(scaled(mouseX), scaled(mouseY), scaled(deltaX), scaled(deltaY)));
        }

        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        preRender(matrices);

        fillGradient(matrices, 0, 0, scaled(width), scaled(height), Theme.getColorBackground().getRGB(), Theme.getColorBackgroundSecondary().getRGB());

        module.hudComponents.forEach((comp) -> comp.render(matrices, scaled(mouseX), scaled(mouseY), true));
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            module.hudComponents.forEach((comp) -> comp.click(scaled(mouseX), scaled(mouseY)));
        }

        return super.mouseReleased(mouseX, mouseY, button);
    }
}
