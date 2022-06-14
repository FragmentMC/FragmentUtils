package stanuwu.fragmentutils.gui.component;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import stanuwu.fragmentutils.utils.LangHelper;
import stanuwu.fragmentutils.gui.SubScreen;
import stanuwu.fragmentutils.modules.Module;

public class RoundedBiModuleCard extends RoundedModuleCard {
    private RoundedButton button2;

    public RoundedBiModuleCard(int x, int y, Module module, SubScreen buttonLink, String buttonNameKey) {
        super(x, y, module);

        this.button2 = new RoundedButtonSmall(40, 20, x - 45, y + oheight / 12, (button) -> {
            MinecraftClient.getInstance().setScreen(buttonLink);
        }, LangHelper.getTranslated(buttonNameKey));
    }

    @Override
    public void init(ComponentGroup componentGroup) {
        componentGroup.add(this.button2);
        super.init(componentGroup);
    }

    @Override
    public void render(MatrixStack poseStack, int mouseX, int mouseY, ComponentGroup componentGroup) {
        super.render(poseStack, mouseX, mouseY, componentGroup, button2.isHovering(mouseX, mouseY, componentGroup));
    }
}
