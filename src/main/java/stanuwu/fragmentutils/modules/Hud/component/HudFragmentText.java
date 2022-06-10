package stanuwu.fragmentutils.modules.Hud.component;

import net.minecraft.client.util.math.MatrixStack;
import stanuwu.fragmentutils.gui.Theme;

public class HudFragmentText extends HudComponent {

    private final String text = "Fragment Utils";

    public HudFragmentText(boolean enabled, double x, double y, int id) {
        super(enabled, x, y, id);
    }

    @Override
    public double getWidth() {
        return Theme.getHudTitleFont().getWidth(text);
    }

    @Override
    public double getHeight() {
        return Theme.getHudTitleFont().getHeight(text);
    }

    @Override
    public void render(MatrixStack poseStack, double mouseX, double mouseY, boolean menu) {
        Theme.getHudTitleFont().drawString(poseStack, text, (float) dX(), (float) dY(), Theme.getColorTitle().getRGB());

        super.render(poseStack, mouseX, mouseY, menu);
    }
}
