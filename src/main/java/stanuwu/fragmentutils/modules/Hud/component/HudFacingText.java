package stanuwu.fragmentutils.modules.Hud.component;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import stanuwu.fragmentutils.utils.StringHelper;
import stanuwu.fragmentutils.gui.Theme;
import stanuwu.fragmentutils.render.font.TTFFontRenderer;

public class HudFacingText extends HudComponent {
    private final String invalid = "N/A";

    public HudFacingText(boolean enabled, double x, double y, String id) {
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
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            return Theme.getHudSubFont().getWidth("Facing: " + StringHelper.toCamelCase(player.getHorizontalFacing().name())) + 2;
        }
        return Theme.getHudSubFont().getWidth(invalid);
    }

    private boolean leftAlign() {
        return x < 0.5;
    }

    @Override
    public void render(MatrixStack poseStack, double mouseX, double mouseY, boolean menu) {
        TTFFontRenderer font = Theme.getHudSubFont();
        double xAdd = leftAlign() ? 0 : getWidth() - getRealWidth();
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            String text = "Facing: ";
            font.drawString(poseStack, text, (float) (dX() + xAdd), (float) dY(), getModule().getPrimaryColor().getRGB());
            xAdd += font.getWidth(text);
            text = StringHelper.toCamelCase(player.getHorizontalFacing().name());
            font.drawString(poseStack, text, (float) (dX() + xAdd), (float) dY(), getModule().getSecondaryColor().getRGB());
        } else {
            font.drawString(poseStack, invalid, (float) (dX() + xAdd), (float) dY(), getModule().getPrimaryColor().getRGB());
        }

        super.render(poseStack, mouseX, mouseY, menu);
    }
}

