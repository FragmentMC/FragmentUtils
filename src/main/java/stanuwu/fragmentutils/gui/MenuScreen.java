package stanuwu.fragmentutils.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import stanuwu.fragmentutils.Utils.LangHelper;
import stanuwu.fragmentutils.gui.component.ComponentGroup;
import stanuwu.fragmentutils.render.font.TTFFontRenderer;


public class MenuScreen extends Screen {
    TTFFontRenderer titleFont = Theme.getTitleFont();
    TTFFontRenderer descFont = Theme.getDescFont();

    protected ComponentGroup components = new ComponentGroup(this);

    protected MenuScreen(Text title) {
        super(title);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        components.clickAll((int) mouseX, (int) mouseY);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        components.stopDragAll();
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        components.dragAll(mouseX, mouseY);
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        components.keyPressAll(keyCode, scanCode, modifiers);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        components.charTypeAll(chr, modifiers);
        return super.charTyped(chr, modifiers);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {

        MatrixStack stack2 = new MatrixStack();
        Identifier identifier = Theme.getFragmentLogo();
        RenderSystem.enableTexture();
        RenderSystem.setShaderTexture(0, identifier);
        stack2.scale(0.18f, 0.18f, 1);
        drawTexture(stack2, (int) (width / 0.18 / 2 - 1050), 20, 0, 0, 256, 256);
        RenderSystem.disableTexture();

        String title = LangHelper.getTranslated("menu.fragment_utils.title");
        titleFont.drawCenteredStringWithShadow(matrices, title, width / 2f + 30, 0, Theme.getColorTitle().getRGB());

        String description = LangHelper.getTranslated("menu.fragment_utils.description");
        descFont.drawString(matrices, description, width - descFont.getWidth(description) - 2, height - descFont.getHeight(description) - 2, Theme.getColorTitle().getRGB());

        components.renderAll(matrices, mouseX, mouseY);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
