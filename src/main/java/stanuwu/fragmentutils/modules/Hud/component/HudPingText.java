package stanuwu.fragmentutils.modules.Hud.component;

import net.minecraft.client.util.math.MatrixStack;
import stanuwu.fragmentutils.gui.Theme;
import stanuwu.fragmentutils.render.font.TTFFontRenderer;
import stanuwu.fragmentutils.trackers.PingTracker;

public class HudPingText extends HudComponent {
    public HudPingText(boolean enabled, double x, double y, String id) {
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
        return Theme.getHudSubFont().getWidth("Ping: " + getPing()) + 2;
    }

    private boolean leftAlign() {
        return x < 0.5;
    }

    private String getPing() {
        return PingTracker.getPing() + "";
    }

    @Override
    public void render(MatrixStack poseStack, double mouseX, double mouseY, boolean menu) {
        TTFFontRenderer font = Theme.getHudSubFont();
        double xAdd = leftAlign() ? 1 : getWidth() - getRealWidth();
        String text = "Ping: ";
        font.drawString(poseStack, text, (float) (dX() + xAdd), (float) dY(), getModule().getPrimaryColor().getRGB());
        xAdd += font.getWidth(text);
        text = getPing();
        font.drawString(poseStack, text, (float) (dX() + xAdd), (float) dY(), getModule().getSecondaryColor().getRGB());

        super.render(poseStack, mouseX, mouseY, menu);
    }
}