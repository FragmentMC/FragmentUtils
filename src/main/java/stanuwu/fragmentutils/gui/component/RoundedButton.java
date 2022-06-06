package stanuwu.fragmentutils.gui.component;

import net.minecraft.client.util.math.MatrixStack;
import stanuwu.fragmentutils.gui.Theme;
import stanuwu.fragmentutils.render.RenderHelper;
import stanuwu.fragmentutils.render.font.TTFFontRenderer;

import java.awt.*;
import java.util.function.Consumer;

public class RoundedButton extends Clickable {
    public String text;
    protected TTFFontRenderer font = Theme.getButtonFont();

    public RoundedButton(int width, int heigth, int x, int y, Consumer<Object> onClick, String text) {
        super(width, heigth, x, y, onClick);
        this.text = text;
    }

    @Override
    public void render(MatrixStack poseStack, int mouseX, int mouseY, ComponentGroup componentGroup) {
        Color buttonColor = this.isHovering(mouseX, mouseY, componentGroup) ? Theme.getColorHover() : Theme.getColorPrimary();
        RenderHelper.rounded_rect(poseStack, this.x, this.y, this.x + width, this.y + height, 7, buttonColor);

        font.drawCenteredString(poseStack, text, this.x + (float) width / 2, this.y + (float) height / 2 - font.getHeight(text) / 2, Theme.getColorText().getRGB());
    }
}
