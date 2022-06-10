package stanuwu.fragmentutils.modules.Hud.component;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import stanuwu.fragmentutils.Utils.ScaleHelper;
import stanuwu.fragmentutils.gui.Theme;
import stanuwu.fragmentutils.render.RenderHelper;

import java.awt.*;

public abstract class HudComponent {
    private boolean enabled;
    private boolean dragged = false;
    protected double x;
    protected double y;
    int id;

    public HudComponent(boolean enabled, double x, double y, int id) {
        this.enabled = enabled;
        this.x = x;
        this.y = y;
        this.id = id;
    }

    protected Window getWindow() {
        return MinecraftClient.getInstance().getWindow();
    }

    protected double maxWidth() {
        return ScaleHelper.scaled(getWindow().getScaledWidth()) - getWidth();
    }

    protected double maxHeight() {
        return ScaleHelper.scaled(getWindow().getScaledHeight()) - getHeight();
    }

    protected double dX() {
        return maxWidth() * x;
    }

    protected double dY() {
        return maxHeight() * y;
    }

    protected double sX(double x) {
        return x / maxWidth();
    }

    protected double sY(double y) {
        return y / maxHeight();
    }

    public void render(MatrixStack poseStack) {
        render(poseStack, -1, -1, false);
    }

    public void render(MatrixStack poseStack, double mouseX, double mouseY, boolean menu) {
        if (menu) renderOverlay(poseStack, isHovering(mouseX, mouseY));
    }

    public void renderOverlay(MatrixStack poseStack, boolean hovering) {
        Color color = enabled ?
                (hovering ? Theme.getColorHudOverlayOnHover() : Theme.getColorHudOverlayOn()) :
                (hovering ? Theme.getColorHudOverlayOffHover() : Theme.getColorHudOverlayOff());
        RenderHelper.rect(poseStack, (int) dX(), (int) dY(), (int) (dX() + getWidth()), (int) (dY() + getHeight()), color);
    }

    public abstract double getWidth();

    public abstract double getHeight();

    public void drag(double mouseX, double mouseY, double deltaX, double deltaY) {
        if (isHovering(mouseX, mouseY)) {
            this.x = MathHelper.clamp(x + sX(deltaX), 0, 1);
            this.y = MathHelper.clamp(y + sY(deltaY), 0, 1);
            dragged = true;
        }
    }

    public boolean isHovering(double mouseX, double mouseY) {
        return mouseX > dX() && mouseX < dX() + getWidth() && mouseY > this.dY() && mouseY < this.dY() + getHeight();
    }

    public void click(double mouseX, double mouseY) {
        if (isHovering(mouseX, mouseY) && !dragged) {
            enabled = !enabled;
        } else {
            dragged = false;
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
