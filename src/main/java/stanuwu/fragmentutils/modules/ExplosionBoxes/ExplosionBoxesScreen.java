package stanuwu.fragmentutils.modules.ExplosionBoxes;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import stanuwu.fragmentutils.Utils.FloatHelper;
import stanuwu.fragmentutils.Utils.LangHelper;
import stanuwu.fragmentutils.gui.SubScreen;
import stanuwu.fragmentutils.gui.Theme;
import stanuwu.fragmentutils.gui.component.RoundedSlider;
import stanuwu.fragmentutils.gui.component.RoundedSwitch;
import stanuwu.fragmentutils.gui.component.RoundedTextbox;
import stanuwu.fragmentutils.gui.component.RoundedTextboxSmall;
import stanuwu.fragmentutils.modules.Modules;
import stanuwu.fragmentutils.render.RenderHelper;

import java.awt.*;

public class ExplosionBoxesScreen extends SubScreen {
    ExplosionBoxesModule module = (ExplosionBoxesModule) Modules.getModule("explosionboxes");

    RoundedSlider sizeSlider = new RoundedSlider(105, 15, -75, -30, (sliderValue) -> {
        setSize((float) ((double) sliderValue));
    }, module.minSize, module.maxSize, module.size);

    RoundedTextbox sizeBox = new RoundedTextboxSmall(30, 15, 110 - 75, -30, (textboxValue) -> {
        float res = FloatHelper.parseFloatSafe((String) textboxValue);
        setSize(res);
    }, Float.toString(module.size));

    RoundedSlider timeSlider = new RoundedSlider(105, 15, -75, 5, (sliderValue) -> {
        setTime((float) ((double) sliderValue));
    }, module.minTime, module.maxTime, module.time);

    RoundedTextbox timeBox = new RoundedTextboxSmall(30, 15, 110 - 75, 5, (textboxValue) -> {
        float res = FloatHelper.parseFloatSafe((String) textboxValue);
        setTime(res);
    }, noDecimalString(module.time));

    final RoundedTextbox redBox = new RoundedTextboxSmall(30, 15, -75, 90, this::acceptRed, noDecimalString(this.module.red));
    final RoundedTextbox greenBox = new RoundedTextboxSmall(30, 15, -40, 90, this::acceptGreen, noDecimalString(this.module.green));
    final RoundedTextbox blueBox = new RoundedTextboxSmall(30, 15, -5, 90, this::acceptBlue, noDecimalString(this.module.blue));
    final RoundedTextbox alphaBox = new RoundedTextboxSmall(30, 15, 30, 90, this::acceptAlpha, noDecimalString(this.module.alpha));

    public ExplosionBoxesScreen(Text title) {
        super(title);

        components.add(new RoundedSwitch(20, 15, -75, -70, (toggle) -> {
            this.module.toggle();
        }, this.module.getEnabled()));

        components.add(sizeSlider);
        components.add(sizeBox);
        components.add(timeSlider);
        components.add(timeBox);

        components.add(new RoundedSwitch(15, 10, -75, 31, (toggle) -> {
            this.module.blockPosition = !this.module.blockPosition;
        }, this.module.blockPosition));

        components.add(new RoundedSwitch(15, 10, -75, 56, (toggle) -> {
            this.module.highlightOrigin = !this.module.highlightOrigin;
        }, this.module.highlightOrigin));

        components.add(redBox);
        components.add(greenBox);
        components.add(blueBox);
        components.add(alphaBox);
    }

    String noDecimalString(float num) {
        return String.format("%d", (int) num);
    }

    private void acceptRed(Object textboxValue) {
        float res = prepColor((String) textboxValue);
        this.module.red = res;
        redBox.setContent(noDecimalString(res));
    }

    private void acceptGreen(Object textboxValue) {
        float res = prepColor((String) textboxValue);
        this.module.green = res;
        greenBox.setContent(noDecimalString(res));
    }

    private void acceptBlue(Object textboxValue) {
        float res = prepColor((String) textboxValue);
        this.module.blue = res;
        blueBox.setContent(noDecimalString(res));
    }

    private void acceptAlpha(Object textboxValue) {
        float res = prepColor((String) textboxValue);
        this.module.alpha = res;
        alphaBox.setContent(noDecimalString(res));
    }

    float prepColor(String text) {
        float res = FloatHelper.parseFloatSafe(text);
        res = MathHelper.clamp(res, 0, 255);
        return Math.round(res);
    }

    void setSize(float size) {
        size = MathHelper.clamp(size, module.minSize, module.maxSize);
        size = FloatHelper.roundToTwoDecimals(size);
        module.size = size;
        sizeSlider.value = size;
        sizeBox.setContent(Float.toString(size));
    }

    void setTime(float time) {
        time = MathHelper.clamp(time, module.minTime, module.maxTime);
        time = Math.round(time);
        module.time = time;
        timeSlider.value = time;
        timeBox.setContent(noDecimalString(time));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        preRender(matrices);

        Vector2f center = components.getCenter();

        fillGradient(matrices, 0, 0, scaled(width), scaled(height), Theme.getColorBackground().getRGB(), Theme.getColorBackgroundSecondary().getRGB());

        RenderHelper.rounded_rect(matrices, (int) center.getX() - 95, (int) center.getY() - 85, (int) center.getX() + 95, (int) center.getY() + 120, 7, Theme.getColorSecondary());
        RenderHelper.rounded_rect(matrices, (int) center.getX() - 95 + 5, (int) center.getY() - 85 + 5, (int) center.getX() + 95 - 5, (int) center.getY() + 120 - 5, 5, Theme.getColorTertiary());

        RenderHelper.rounded_rect(matrices, (int) center.getX() + 65, (int) center.getY() + 90, (int) center.getX() + 65 + 15, (int) center.getY() + 90 + 15, 7, Color.WHITE);
        RenderHelper.rounded_rect(matrices, (int) center.getX() + 65, (int) center.getY() + 90, (int) center.getX() + 65 + 15, (int) center.getY() + 90 + 15, 7, new Color((int) module.red, (int) module.blue, (int) module.green, (int) module.alpha));
        RenderHelper.rounded_rect(matrices, (int) center.getX() + 65, (int) center.getY() + 90, (int) center.getX() + 65 + 15, (int) center.getY() + 90 + 15, 7, new Color((int) module.red, (int) module.blue, (int) module.green, (int) module.alpha));

        Theme.getSubFont().drawString(matrices, LangHelper.getTranslated("module.fragment_utils.name.explosionboxes"), center.getX() - 50, center.getY() - 70, Theme.getColorText().getRGB());
        Theme.getButtonFont().drawString(matrices, LangHelper.getTranslated("menu.fragment_utils.option.size"), center.getX() - 75, center.getY() - 45, Theme.getColorText().getRGB());
        Theme.getButtonFont().drawString(matrices, LangHelper.getTranslated("menu.fragment_utils.option.alive_time"), center.getX() - 75, center.getY() - 10, Theme.getColorText().getRGB());
        Theme.getButtonFont().drawString(matrices, LangHelper.getTranslated("menu.fragment_utils.option.block_position"), center.getX() - 55, center.getY() + 30, Theme.getColorText().getRGB());
        Theme.getButtonFont().drawString(matrices, LangHelper.getTranslated("menu.fragment_utils.option.highlight_origin"), center.getX() - 55, center.getY() + 55, Theme.getColorText().getRGB());
        Theme.getButtonFont().drawString(matrices, LangHelper.getTranslated("menu.fragment_utils.option.color"), center.getX() - 75, center.getY() + 75, Theme.getColorText().getRGB());

        super.render(matrices, mouseX, mouseY, delta);
    }
}