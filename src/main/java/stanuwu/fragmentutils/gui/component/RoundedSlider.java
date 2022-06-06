package stanuwu.fragmentutils.gui.component;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.math.MathHelper;
import stanuwu.fragmentutils.gui.Theme;
import stanuwu.fragmentutils.render.RenderHelper;

import java.awt.*;
import java.util.function.Consumer;

public class RoundedSlider extends Clickable implements Draggable {
    public double min;
    public double max;
    public double value;

    public RoundedSlider(int width, int heigth, int x, int y, Consumer<Object> onClick, double min, double max, double value) {
        super(width, heigth, x, y, onClick);
        this.min = min;
        this.max = max;
        this.value = value;
    }

    @Override
    public void render(MatrixStack poseStack, int mouseX, int mouseY, ComponentGroup componentGroup) {
        Color sliderColor = this.isHoveringSlider(mouseX, mouseY, componentGroup) ? Theme.getColorHoverSecondary() : Theme.getColorSecondary();
        int sliderPos = (int) (x + getSliderPos());
        RenderHelper.rounded_rect(poseStack, x, y, x + width, y + height, 7, Theme.getColorPrimary());
        RenderHelper.rounded_rect(poseStack, sliderPos, y, Math.round(sliderPos + getSliderWidth()), y + height, 7, sliderColor);
        if (isDragging.get() && isHovering(mouseX, mouseY, componentGroup)) {
            drag(mouseX, mouseY, componentGroup);
        }
    }

    @Override
    public void drag(double mouseX, double mouseY, ComponentGroup componentGroup) {
        if (isDragging.get()) {
            Vector2f center = componentGroup.getCenter();
            mouseX -= center.getX();
            double start = this.x + getSliderWidth() / 2;
            double end = this.x + width - getSliderWidth() / 2;
            setDisplayValue((((mouseX - start) / (end - start)) * (max - min)) + min);
            sendClick();
        }
    }

    @Override
    public void stopDrag() {
        isDragging.set(false);
    }

    double getDisplayValue() {
        return MathHelper.clamp(this.value, this.min, this.max);
    }

    void setDisplayValue(double value) {
        this.value = MathHelper.clamp(value, this.min, this.max);
    }

    double getSliderPos() {
        return Math.round((width - getSliderWidth()) * ((getDisplayValue() - min) / (max - min)));
    }

    @Override
    public void onClick() {
        isDragging.set(true);
        sendClick();
    }

    void sendClick() {
        onClick.accept(value);
    }

    float getSliderWidth() {
        return (float) this.width / 7;
    }

    boolean isHoveringSlider(double mouseX, double mouseY, ComponentGroup componentGroup) {
        Vector2f center = componentGroup.getCenter();
        mouseX -= center.getX();
        mouseY -= center.getY();
        double sliderPos = this.x + getSliderPos();
        double sliderWidth = getSliderWidth();
        return (mouseX >= sliderPos && mouseX <= sliderPos + sliderWidth && mouseY >= this.y && mouseY <= this.y + height);
    }
}
