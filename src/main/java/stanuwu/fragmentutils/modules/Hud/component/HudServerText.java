package stanuwu.fragmentutils.modules.Hud.component;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import stanuwu.fragmentutils.gui.Theme;
import stanuwu.fragmentutils.render.font.TTFFontRenderer;

public class HudServerText extends HudComponent {
    private final String invalid = "N/A";

    public HudServerText(boolean enabled, double x, double y, String id) {
        super(enabled, x, y, id);
    }

    @Override
    public double getWidth() {
        return 125;
    }

    @Override
    public double getHeight() {
        return Theme.getHudSubFont().getMonoHeight();
    }

    private double getRealWidth() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            return Theme.getHudSubFont().getWidth("IP: " + getServerName(client)) + 2;
        }
        return Theme.getHudSubFont().getWidth(invalid);
    }

    private boolean leftAlign() {
        return x < 0.5;
    }

    private String getServerName(MinecraftClient client) {

        if (client.getCurrentServerEntry() != null) {
            return client.getCurrentServerEntry().address;
        } else {
            return "Singleplayer";
        }

    }

    @Override
    public void render(MatrixStack poseStack, double mouseX, double mouseY, boolean menu) {
        TTFFontRenderer font = Theme.getHudSubFont();
        double xAdd = leftAlign() ? 1 : getWidth() - getRealWidth();

        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            String text = "IP: ";
            font.drawString(poseStack, text, (float) (dX() + xAdd), (float) dY(), getModule().getPrimaryColor().getRGB());
            xAdd += font.getWidth(text);
            text = getServerName(client);
            font.drawString(poseStack, text, (float) (dX() + xAdd), (float) dY(), getModule().getSecondaryColor().getRGB());
        } else {
            font.drawString(poseStack, invalid, (float) (dX() + xAdd), (float) dY(), getModule().getSecondaryColor().getRGB());
        }

        super.render(poseStack, mouseX, mouseY, menu);
    }
}