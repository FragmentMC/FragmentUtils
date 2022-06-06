package stanuwu.fragmentutils.gui;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import stanuwu.fragmentutils.gui.component.RoundedModuleCard;
import stanuwu.fragmentutils.modules.Modules;

public class SelectScreen extends MenuScreen {
    protected SelectScreen(Text title) {
        super(title);

        components.add(new RoundedModuleCard(0, 0, Modules.getModule("explosionboxes")));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        fillGradient(matrices, 0, 0, width, height, Theme.getColorBackground().getRGB(), Theme.getColorBackgroundSecondary().getRGB());

        super.render(matrices, mouseX, mouseY, delta);
    }
}
