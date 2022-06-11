package stanuwu.fragmentutils.modules.Hud.component;

import net.minecraft.client.util.math.MatrixStack;
import stanuwu.fragmentutils.gui.Theme;
import stanuwu.fragmentutils.render.RenderHelper;

public class HudFragmentLogo extends HudComponent {
    private float scale = 0.15f;

    public HudFragmentLogo(boolean enabled, double x, double y, String id) {
        super(enabled, x, y, id);
    }

    @Override
    public double getWidth() {
        return 256 * scale;
    }

    @Override
    public double getHeight() {
        return 256 * scale;
    }

    @Override
    public void render(MatrixStack poseStack, double mouseX, double mouseY, boolean menu) {
        RenderHelper.textureFull(poseStack, (int) (dX() / scale), (int) (dY() / scale), scale, scale, Theme.getFragmentLogo());

        super.render(poseStack, mouseX, mouseY, menu);
    }
}
