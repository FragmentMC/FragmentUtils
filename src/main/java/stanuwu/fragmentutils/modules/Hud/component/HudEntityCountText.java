package stanuwu.fragmentutils.modules.Hud.component;

import net.minecraft.client.util.math.MatrixStack;
import stanuwu.fragmentutils.gui.Theme;
import stanuwu.fragmentutils.render.font.TTFFontRenderer;
import stanuwu.fragmentutils.trackers.EntityTracker;

public class HudEntityCountText extends HudComponent {
    public HudEntityCountText(boolean enabled, double x, double y, String id) {
        super(enabled, x, y, id);
    }

    @Override
    public double getWidth() {
        return 75;
    }

    @Override
    public double getHeight() {
        return Theme.getHudSubFont().getMonoHeight() * 2;
    }

    private double getRealWidth() {
        return Math.max(Theme.getHudSubFont().getWidth("TNT: " + getTnt()), Theme.getHudSubFont().getWidth("Sand: " + getSand())) + 2;
    }

    private boolean leftAlign() {
        return x < 0.5;
    }

    private String getTnt() {
        return EntityTracker.getTnt() + "";
    }

    private String getSand() {
        return EntityTracker.getSand() + "";
    }

    @Override
    public void render(MatrixStack poseStack, double mouseX, double mouseY, boolean menu) {
        TTFFontRenderer font = Theme.getHudSubFont();
        double xAddO = leftAlign() ? 1 : getWidth() - getRealWidth();
        double xAdd = xAddO;
        double yAdd = this.getHeight() / 2;
        String text = "TNT: ";
        font.drawString(poseStack, text, (float) (dX() + xAdd), (float) dY(), getModule().getPrimaryColor().getRGB());
        xAdd += font.getWidth(text);
        text = getTnt();
        font.drawString(poseStack, text, (float) (dX() + xAdd), (float) dY(), getModule().getSecondaryColor().getRGB());
        xAdd = xAddO;
        text = "Sand: ";
        font.drawString(poseStack, text, (float) (dX() + xAdd), (float) (dY() + yAdd), getModule().getPrimaryColor().getRGB());
        xAdd += font.getWidth(text);
        text = getSand();
        font.drawString(poseStack, text, (float) (dX() + xAdd), (float) (dY() + yAdd), getModule().getSecondaryColor().getRGB());

        super.render(poseStack, mouseX, mouseY, menu);
    }
}