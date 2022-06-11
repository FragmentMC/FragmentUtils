package stanuwu.fragmentutils.modules.Hud.component;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import stanuwu.fragmentutils.gui.Theme;
import stanuwu.fragmentutils.render.font.TTFFontRenderer;

import java.text.DecimalFormat;

public class HudCoordinateText extends HudComponent {
    private String invalid = "N/A";

    public HudCoordinateText(boolean enabled, double x, double y, String id) {
        super(enabled, x, y, id);
    }

    @Override
    public double getWidth() {
        return 145;
    }

    @Override
    public double getHeight() {
        return Theme.getHudSubFont().getMonoHeight();
    }

    private double getRealWidth() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            TTFFontRenderer font = Theme.getHudSubFont();
            double width = font.getWidth("X:   Y:   Z:  ");
            width += font.getWidth(numToString(player.getX()));
            width += font.getWidth(numToString(player.getY()));
            width += font.getWidth(numToString(player.getZ()));
            return width + 1;
        }
        return Theme.getHudSubFont().getWidth(invalid);
    }

    private String numToString(double num) {
        DecimalFormat decimalFormat = new DecimalFormat("#");
        return decimalFormat.format(num);
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
            //x
            String text = "X: ";
            font.drawString(poseStack, text, (float) (dX() + xAdd), (float) dY(), getModule().getPrimaryColor().getRGB());
            xAdd += font.getWidth(text);
            text = numToString(player.getX());
            font.drawString(poseStack, text, (float) (dX() + xAdd), (float) dY(), getModule().getSecondaryColor().getRGB());
            xAdd += font.getWidth(text);

            //y
            text = "  Y: ";
            font.drawString(poseStack, text, (float) (dX() + xAdd), (float) dY(), getModule().getPrimaryColor().getRGB());
            xAdd += font.getWidth(text);
            text = numToString(player.getY());
            font.drawString(poseStack, text, (float) (dX() + xAdd), (float) dY(), getModule().getSecondaryColor().getRGB());
            xAdd += font.getWidth(text);

            //z
            text = "  Z: ";
            font.drawString(poseStack, text, (float) (dX() + xAdd), (float) dY(), getModule().getPrimaryColor().getRGB());
            xAdd += font.getWidth(text);
            text = numToString(player.getZ());
            font.drawString(poseStack, text, (float) (dX() + xAdd), (float) dY(), getModule().getSecondaryColor().getRGB());
        } else {
            font.drawString(poseStack, invalid, (float) (dX() + xAdd), (float) dY(), getModule().getPrimaryColor().getRGB());
        }

        super.render(poseStack, mouseX, mouseY, menu);
    }
}
