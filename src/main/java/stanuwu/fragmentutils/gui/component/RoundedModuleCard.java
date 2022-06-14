package stanuwu.fragmentutils.gui.component;

import net.minecraft.client.util.math.MatrixStack;
import stanuwu.fragmentutils.utils.LangHelper;
import stanuwu.fragmentutils.gui.Theme;
import stanuwu.fragmentutils.modules.Module;
import stanuwu.fragmentutils.render.RenderHelper;

import java.awt.*;

public class RoundedModuleCard extends Toggleable implements Initializeable {
    public Module module;

    static int owidth = 130;
    static int oheight = 60;

    RoundedButton button;

    boolean click = false;

    public RoundedModuleCard(int x, int y, Module module) {
        super(owidth, oheight, x - owidth / 2, y - oheight / 2, (card) -> {
            ((RoundedModuleCard) card).module.toggle();
        }, module.getEnabled());
        this.module = module;

        this.button = new RoundedButtonSmall(60, 20, x, y + oheight / 12, (button) -> {
            module.openSettings();
        }, LangHelper.getTranslated("button.fragment_utils.settings"));
    }

    @Override
    public void onClick() {
        click = true;
    }

    public void onClick(double mouseX, double mouseY, ComponentGroup componentGroup, boolean invalidHover) {
        if (!button.isHovering(mouseX, mouseY, componentGroup) && !invalidHover) {
            super.onClick();
        }
    }

    @Override
    public void init(ComponentGroup componentGroup) {
        componentGroup.add(this.button);
    }

    @Override
    public void render(MatrixStack poseStack, int mouseX, int mouseY, ComponentGroup componentGroup) {
        render(poseStack, mouseX, mouseY, componentGroup, false);
    }

    public void render(MatrixStack poseStack, int mouseX, int mouseY, ComponentGroup componentGroup, boolean invalidHover) {
        Color color = isHovering(mouseX, mouseY, componentGroup) && !button.isHovering(mouseX, mouseY, componentGroup) && !invalidHover ?
                (!active ? Theme.getColorHover() : Theme.getColorHoverSecondary()) :
                (!active ? Theme.getColorPrimary() : Theme.getColorSecondary());
        RenderHelper.rounded_rect(poseStack, x, y, x + width, y + height, 7, color);
        RenderHelper.rounded_rect(poseStack, x + 3, y + 3, x + width - 3, y + height - 3, 5, active ? Theme.getColorOverlay() : Theme.getColorTertiary());
        Theme.getButtonFont().drawCenteredString(poseStack, LangHelper.getTranslated(module.getName()), x + width / 2f, y + height / 12f, Theme.getColorText().getRGB());
        Theme.getDescFont().drawCenteredString(poseStack, LangHelper.getTranslated(module.getDescription()), x + width / 2f, y + height / 3f, Theme.getColorText().getRGB());

        if (click) {
            click = false;
            onClick(mouseX, mouseY, componentGroup, invalidHover);
        }
    }
}
