package stanuwu.fragmentutils.modules.Hud.component;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import stanuwu.fragmentutils.gui.Theme;
import stanuwu.fragmentutils.render.font.TTFFontRenderer;

public class HudFpsText extends HudComponent {
    private final String invalid = "N/A";

    public HudFpsText(boolean enabled, double x, double y, String id) {
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
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            return Theme.getHudSubFont().getWidth("FPS: " + getFps(client)) + 2;
        }
        return Theme.getHudSubFont().getWidth(invalid);
    }

    private boolean leftAlign() {
        return x < 0.5;
    }

    private String getFps(MinecraftClient client) {
        return client.fpsDebugString.split(" ")[0];
    }

    @Override
    public void render(MatrixStack poseStack, double mouseX, double mouseY, boolean menu) {
        TTFFontRenderer font = Theme.getHudSubFont();
        double xAdd = leftAlign() ? 1 : getWidth() - getRealWidth();
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            String text = "FPS: ";
            font.drawString(poseStack, text, (float) (dX() + xAdd), (float) dY(), getModule().getPrimaryColor().getRGB());
            xAdd += font.getWidth(text);
            text = getFps(client);
            font.drawString(poseStack, text, (float) (dX() + xAdd), (float) dY(), getModule().getSecondaryColor().getRGB());
        } else {
            font.drawString(poseStack, invalid, (float) (dX() + xAdd), (float) dY(), getModule().getPrimaryColor().getRGB());
        }

        super.render(poseStack, mouseX, mouseY, menu);
    }
}
