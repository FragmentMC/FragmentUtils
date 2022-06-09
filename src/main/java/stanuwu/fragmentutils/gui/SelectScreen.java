package stanuwu.fragmentutils.gui;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import stanuwu.fragmentutils.gui.component.RoundedModuleCard;
import stanuwu.fragmentutils.modules.Modules;

public class SelectScreen extends MenuScreen {
    protected SelectScreen(Text title) {
        super(title);

        components.add(new RoundedModuleCard(-70, -35, Modules.getModule("explosionboxes")));
        components.add(new RoundedModuleCard(70, -35, Modules.getModule("patchcrumbs")));
        components.add(new RoundedModuleCard(-70, 35, Modules.getModule("breadcrumbs")));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        preRender(matrices);

        fillGradient(matrices, 0, 0, scaled(width), scaled(height), Theme.getColorBackground().getRGB(), Theme.getColorBackgroundSecondary().getRGB());

        super.render(matrices, mouseX, mouseY, delta);
    }
}
