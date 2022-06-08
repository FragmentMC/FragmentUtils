package stanuwu.fragmentutils.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import stanuwu.fragmentutils.Utils.LangHelper;
import stanuwu.fragmentutils.gui.component.ComponentGroup;
import stanuwu.fragmentutils.render.RenderHelper;
import stanuwu.fragmentutils.render.font.TTFFontRenderer;


public class MenuScreen extends Screen {
    TTFFontRenderer titleFont = Theme.getTitleFont();
    TTFFontRenderer descFont = Theme.getDescFont();

    protected Window window;

    protected ComponentGroup components = new ComponentGroup(this);

    protected MenuScreen(Text title) {
        super(title);

        window = MinecraftClient.getInstance().getWindow();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        mouseX = scaled(mouseX);
        mouseY = scaled(mouseY);
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
        mouseX = scaled(mouseX);
        mouseY = scaled(mouseY);
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

    protected void preRender(MatrixStack matrices) {
        float scale = (float) (2.0 / window.getScaleFactor());
        matrices.scale(scale, scale, 1);
        matrices.push();
    }

    public int scaled(int num) {
        return (int) Math.ceil((window.getScaleFactor() / 2d) * num);
    }

    public double scaled(double num) {
        return (window.getScaleFactor() / 2d) * num;
    }

    public int getScaledWidth() {
        return scaled(width);
    }

    public int getScaledHeight() {
        return scaled(height);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderHelper.textureFull(matrices, (int) (scaled(width) / 0.18 / 2 - 1050), 20, 0.18f, 0.18f, Theme.getFragmentLogo());
        mouseX = scaled(mouseX);
        mouseY = scaled(mouseY);

        String title = LangHelper.getTranslated("menu.fragment_utils.title");
        titleFont.drawCenteredStringWithShadow(matrices, title, scaled(width) / 2f + 30, 0, Theme.getColorTitle().getRGB());

        String description = LangHelper.getTranslated("menu.fragment_utils.description");
        descFont.drawString(matrices, description, scaled(width) - descFont.getWidth(description) - 2, scaled(height) - descFont.getHeight(description) - 2, Theme.getColorTitle().getRGB());

        components.renderAll(matrices, mouseX, mouseY);
        matrices.pop();
        super.render(matrices, mouseX, mouseY, delta);
    }
}
