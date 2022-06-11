package stanuwu.fragmentutils.modules.Hud.component;

import net.minecraft.client.util.math.MatrixStack;
import stanuwu.fragmentutils.gui.Theme;
import stanuwu.fragmentutils.render.font.TTFFontRenderer;
import stanuwu.fragmentutils.trackers.TpsTracker;

import java.text.DecimalFormat;

public class HudTpsText extends HudComponent {
    public HudTpsText(boolean enabled, double x, double y, String id) {
        super(enabled, x, y, id);
    }

    @Override
    public double getWidth() {
        return 75;
    }

    @Override
    public double getHeight() {
        return Theme.getHudSubFont().getMonoHeight();
    }

    private double getRealWidth() {
        return Theme.getHudSubFont().getWidth("TPS: " + getTps()) + 2;
    }

    private boolean leftAlign() {
        return x < 0.5;
    }

    private String getTps() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(TpsTracker.getTps());
    }

    @Override
    public void render(MatrixStack poseStack, double mouseX, double mouseY, boolean menu) {
        TTFFontRenderer font = Theme.getHudSubFont();
        double xAdd = leftAlign() ? 1 : getWidth() - getRealWidth();
        String text = "TPS: ";
        font.drawString(poseStack, text, (float) (dX() + xAdd), (float) dY(), getModule().getPrimaryColor().getRGB());
        xAdd += font.getWidth(text);
        text = getTps();
        font.drawString(poseStack, text, (float) (dX() + xAdd), (float) dY(), getModule().getSecondaryColor().getRGB());

        super.render(poseStack, mouseX, mouseY, menu);
    }
}
