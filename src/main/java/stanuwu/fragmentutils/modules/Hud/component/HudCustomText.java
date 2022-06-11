package stanuwu.fragmentutils.modules.Hud.component;

import net.minecraft.client.util.math.MatrixStack;
import stanuwu.fragmentutils.gui.Theme;
import stanuwu.fragmentutils.modules.Hud.HudConfig;

public class HudCustomText extends HudComponent {

    public HudCustomText(boolean enabled, double x, double y, String id) {
        super(enabled, x, y, id);
    }

    @Override
    public double getWidth() {
        return 150;
    }

    @Override
    public double getHeight() {
        return Theme.getHudSubFont().getMonoHeight();
    }

    private double getRealWidth() {
        return Theme.getHudSubFont().getWidth(getText()) + 2;
    }

    private boolean leftAlign() {
        return x < 0.5;
    }

    private String getText() {
        String text = getModule().getCustomText();
        if (text.isBlank()) {
            return HudConfig.customText.getDefaultValue();
        }
        return text;
    }

    @Override
    public void render(MatrixStack poseStack, double mouseX, double mouseY, boolean menu) {
        double xAdd = leftAlign() ? 1 : getWidth() - getRealWidth();
        Theme.getHudSubFont().drawString(poseStack, getText(), (float) (dX() + xAdd), (float) dY(), getModule().getPrimaryColor().getRGB());

        super.render(poseStack, mouseX, mouseY, menu);
    }
}