package stanuwu.fragmentutils.modules.PatchCrumbs;

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

public class PatchCrumbsScreen extends SubScreen {
    PatchCrumbsModule module = (PatchCrumbsModule) Modules.getModule("patchcrumbs");

    RoundedSlider sizeSlider = new RoundedSlider(105, 15, -85, -30, (sliderValue) -> {
        setSize((float) ((double) sliderValue));
    }, module.minSize, module.maxSize, module.size);

    RoundedTextbox sizeBox = new RoundedTextboxSmall(30, 15, 110 - 85, -30, (textboxValue) -> {
        float res = FloatHelper.parseFloatSafe((String) textboxValue);
        setSize(res);
    }, Float.toString(module.size));

    RoundedSlider timeSlider = new RoundedSlider(105, 15, -85, 5, (sliderValue) -> {
        setTime((float) ((double) sliderValue));
    }, module.minTime, module.maxTime, module.time);

    RoundedTextbox timeBox = new RoundedTextboxSmall(30, 15, 110 - 85, 5, (textboxValue) -> {
        float res = FloatHelper.parseFloatSafe((String) textboxValue);
        setTime(res);
    }, noDecimalString(module.time));

    RoundedSlider ySlider = new RoundedSlider(105, 15, -85, -20, (sliderValue) -> {
        setYOffset((float) ((double) sliderValue));
    }, module.minOffset, module.maxOffset, module.y_offset);

    RoundedTextbox yBox = new RoundedTextboxSmall(30, 15, 110 - 85, -20, (textboxValue) -> {
        float res = FloatHelper.parseFloatSafe((String) textboxValue);
        setYOffset(res);
    }, noDecimalString(module.y_offset));

    final RoundedTextbox redBox = new RoundedTextboxSmall(30, 15, -85, 90, this::acceptRed, noDecimalString(this.module.red));
    final RoundedTextbox greenBox = new RoundedTextboxSmall(30, 15, -50, 90, this::acceptGreen, noDecimalString(this.module.green));
    final RoundedTextbox blueBox = new RoundedTextboxSmall(30, 15, -15, 90, this::acceptBlue, noDecimalString(this.module.blue));
    final RoundedTextbox alphaBox = new RoundedTextboxSmall(30, 15, 20, 90, this::acceptAlpha, noDecimalString(this.module.alpha));

    public PatchCrumbsScreen(Text title) {
        super(title);

        components.add(new RoundedSwitch(20, 15, -85, -70, (toggle) -> {
            this.module.toggle();
        }, this.module.getEnabled()));

        components.add(sizeSlider);
        components.add(sizeBox);
        components.add(timeSlider);
        components.add(timeBox);
        components.add(ySlider);
        components.add(yBox);

        components.add(new RoundedSwitch(15, 10, -85, 31, (toggle) -> {
            this.module.path = !this.module.path;
        }, this.module.path));

        components.add(new RoundedSwitch(15, 10, 0, 31, (toggle) -> {
            this.module.path_sideways = !this.module.path_sideways;
        }, this.module.path_sideways));

        components.add(new RoundedSwitch(15, 10, -85, 56, (toggle) -> {
            this.module.tracers = !this.module.tracers;
        }, this.module.tracers));

        components.add(new RoundedSwitch(15, 10, 0, 56, (toggle) -> {
            this.module.sand = !this.module.sand;
        }, this.module.sand));

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

    void setYOffset(float y) {
        y = MathHelper.clamp(y, module.minOffset, module.maxOffset);
        y = Math.round(y);
        module.y_offset = y;
        ySlider.value = y;
        yBox.setContent(noDecimalString(y));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        Vector2f center = components.getCenter();

        fillGradient(matrices, 0, 0, width, height, Theme.getColorBackground().getRGB(), Theme.getColorBackgroundSecondary().getRGB());

        RenderHelper.rounded_rect(matrices, (int) center.getX() - 115, (int) center.getY() - 100, (int) center.getX() + 105, (int) center.getY() + 140, 7, Theme.getColorSecondary());
        RenderHelper.rounded_rect(matrices, (int) center.getX() - 115 + 5, (int) center.getY() - 100 + 5, (int) center.getX() + 105 - 5, (int) center.getY() + 140 - 5, 5, Theme.getColorTertiary());

        RenderHelper.rounded_rect(matrices, (int) center.getX() + 55, (int) center.getY() + 90, (int) center.getX() + 55 + 15, (int) center.getY() + 90 + 15, 7, Color.WHITE);
        RenderHelper.rounded_rect(matrices, (int) center.getX() + 55, (int) center.getY() + 90, (int) center.getX() + 55 + 15, (int) center.getY() + 90 + 15, 7, new Color((int) module.red, (int) module.blue, (int) module.green, (int) module.alpha));
        RenderHelper.rounded_rect(matrices, (int) center.getX() + 55, (int) center.getY() + 90, (int) center.getX() + 55 + 15, (int) center.getY() + 90 + 15, 7, new Color((int) module.red, (int) module.blue, (int) module.green, (int) module.alpha));

        Theme.getSubFont().drawString(matrices, LangHelper.getTranslated("module.fragment_utils.name.patchcrumbs"), center.getX() - 60, center.getY() - 70, Theme.getColorText().getRGB());
        Theme.getButtonFont().drawString(matrices, LangHelper.getTranslated("menu.fragment_utils.option.size"), center.getX() - 85, center.getY() - 45, Theme.getColorText().getRGB());
        Theme.getButtonFont().drawString(matrices, LangHelper.getTranslated("menu.fragment_utils.option.alive_time"), center.getX() - 85, center.getY() - 10, Theme.getColorText().getRGB());
        Theme.getButtonFont().drawString(matrices, LangHelper.getTranslated("menu.fragment_utils.option.path"), center.getX() - 65, center.getY() + 30, Theme.getColorText().getRGB());
        Theme.getButtonFont().drawString(matrices, LangHelper.getTranslated("menu.fragment_utils.option.path_sideways"), center.getX() + 20, center.getY() + 30, Theme.getColorText().getRGB());
        Theme.getButtonFont().drawString(matrices, LangHelper.getTranslated("menu.fragment_utils.option.tracers"), center.getX() - 65, center.getY() + 55, Theme.getColorText().getRGB());
        Theme.getButtonFont().drawString(matrices, LangHelper.getTranslated("menu.fragment_utils.option.sand"), center.getX() + 20, center.getY() + 55, Theme.getColorText().getRGB());
        Theme.getButtonFont().drawString(matrices, LangHelper.getTranslated("menu.fragment_utils.option.color"), center.getX() - 85, center.getY() + 75, Theme.getColorText().getRGB());

        super.render(matrices, mouseX, mouseY, delta);
    }
}
