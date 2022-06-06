package stanuwu.fragmentutils.gui.component;

import net.minecraft.client.util.math.MatrixStack;
import stanuwu.fragmentutils.gui.Theme;
import stanuwu.fragmentutils.render.RenderHelper;

import java.awt.*;
import java.util.function.Consumer;

public class RoundedSwitch extends Toggleable {

    public RoundedSwitch(int width, int heigth, int x, int y, Consumer<Object> onClick, boolean active) {
        super(width, heigth, x, y, onClick, active);
    }

    @Override
    public void render(MatrixStack poseStack, int mouseX, int mouseY, ComponentGroup componentGroup) {
        boolean hovering = isHovering(mouseX, mouseY, componentGroup);
        Color highlighColor = hovering ?
                (this.active ? Theme.getColorHoverSecondary() : Theme.getColorHover()) :
                (this.active ? Theme.getColorSecondary() : Theme.getColorPrimary());
        int sliderX = this.active ? this.width / 3 : 0;
        RenderHelper.rounded_rect(poseStack, x, y, x + width, y + height, (float) width / 10, Theme.getColorInactive());
        RenderHelper.rounded_rect(poseStack, x + sliderX, y, x + (int) Math.ceil((float) width / 3 * 2) + sliderX, y + height, (float) width / 10, highlighColor);
    }
}
