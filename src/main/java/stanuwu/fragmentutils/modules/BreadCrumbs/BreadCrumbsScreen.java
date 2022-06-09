package stanuwu.fragmentutils.modules.BreadCrumbs;

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

public class BreadCrumbsScreen extends SubScreen {
    BreadCrumbsModule module = (BreadCrumbsModule) Modules.getModule("breadcrumbs");

    RoundedSlider timeSlider = new RoundedSlider(105, 15, -75, -30, (sliderValue) -> {
        setTime((float) ((double) sliderValue));
    }, module.minTime, module.maxTime, module.time);

    RoundedTextbox timeBox = new RoundedTextboxSmall(30, 15, 110 - 75, -30, (textboxValue) -> {
        float res = FloatHelper.parseFloatSafe((String) textboxValue);
        setTime(res);
    }, noDecimalString(module.time));

    final RoundedTextbox redTntBox = new RoundedTextboxSmall(30, 15, -75, 90, this::acceptTntRed, noDecimalString(this.module.tnt_red));
    final RoundedTextbox greenTntBox = new RoundedTextboxSmall(30, 15, -40, 90, this::acceptTntGreen, noDecimalString(this.module.tnt_green));
    final RoundedTextbox blueTntBox = new RoundedTextboxSmall(30, 15, -5, 90, this::acceptTntBlue, noDecimalString(this.module.tnt_blue));
    final RoundedTextbox alphaTntBox = new RoundedTextboxSmall(30, 15, 30, 90, this::acceptTntAlpha, noDecimalString(this.module.tnt_alpha));

    final RoundedTextbox redSandBox = new RoundedTextboxSmall(30, 15, -75, 120, this::acceptSandRed, noDecimalString(this.module.sand_red));
    final RoundedTextbox greenSandBox = new RoundedTextboxSmall(30, 15, -40, 120, this::acceptSandGreen, noDecimalString(this.module.sand_green));
    final RoundedTextbox blueSandBox = new RoundedTextboxSmall(30, 15, -5, 120, this::acceptSandBlue, noDecimalString(this.module.sand_blue));
    final RoundedTextbox alphaSandBox = new RoundedTextboxSmall(30, 15, 30, 120, this::acceptSandAlpha, noDecimalString(this.module.sand_alpha));

    public BreadCrumbsScreen(Text title) {
        super(title);

        components.add(new RoundedSwitch(20, 15, -75, -70, (toggle) -> {
            this.module.toggle();
        }, this.module.getEnabled()));

        components.add(timeSlider);
        components.add(timeBox);

        components.add(new RoundedSwitch(15, 10, -75, 31, (toggle) -> {
            this.module.tnt = !this.module.tnt;
        }, this.module.tnt));

        components.add(new RoundedSwitch(15, 10, -75, 56, (toggle) -> {
            this.module.sand = !this.module.sand;
        }, this.module.sand));

        components.add(redTntBox);
        components.add(greenTntBox);
        components.add(blueTntBox);
        components.add(alphaTntBox);
        components.add(redSandBox);
        components.add(greenSandBox);
        components.add(blueSandBox);
        components.add(alphaSandBox);
    }

    String noDecimalString(float num) {
        return String.format("%d", (int) num);
    }

    private void acceptTntRed(Object textboxValue) {
        float res = prepColor((String) textboxValue);
        this.module.tnt_red = res;
        redTntBox.setContent(noDecimalString(res));
    }

    private void acceptTntGreen(Object textboxValue) {
        float res = prepColor((String) textboxValue);
        this.module.tnt_green = res;
        greenTntBox.setContent(noDecimalString(res));
    }

    private void acceptTntBlue(Object textboxValue) {
        float res = prepColor((String) textboxValue);
        this.module.tnt_blue = res;
        blueTntBox.setContent(noDecimalString(res));
    }

    private void acceptTntAlpha(Object textboxValue) {
        float res = prepColor((String) textboxValue);
        this.module.tnt_alpha = res;
        alphaTntBox.setContent(noDecimalString(res));
    }

    private void acceptSandRed(Object textboxValue) {
        float res = prepColor((String) textboxValue);
        this.module.sand_red = res;
        redSandBox.setContent(noDecimalString(res));
    }

    private void acceptSandGreen(Object textboxValue) {
        float res = prepColor((String) textboxValue);
        this.module.sand_green = res;
        greenSandBox.setContent(noDecimalString(res));
    }

    private void acceptSandBlue(Object textboxValue) {
        float res = prepColor((String) textboxValue);
        this.module.sand_blue = res;
        blueSandBox.setContent(noDecimalString(res));
    }

    private void acceptSandAlpha(Object textboxValue) {
        float res = prepColor((String) textboxValue);
        this.module.sand_alpha = res;
        alphaSandBox.setContent(noDecimalString(res));
    }

    float prepColor(String text) {
        float res = FloatHelper.parseFloatSafe(text);
        res = MathHelper.clamp(res, 0, 255);
        return Math.round(res);
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
        RenderHelper.rounded_rect(matrices, (int) center.getX() + 65, (int) center.getY() + 90, (int) center.getX() + 65 + 15, (int) center.getY() + 90 + 15, 7, new Color((int) module.tnt_red, (int) module.tnt_blue, (int) module.tnt_green, (int) module.tnt_alpha));
        RenderHelper.rounded_rect(matrices, (int) center.getX() + 65, (int) center.getY() + 90, (int) center.getX() + 65 + 15, (int) center.getY() + 90 + 15, 7, new Color((int) module.tnt_red, (int) module.tnt_blue, (int) module.tnt_green, (int) module.tnt_alpha));

        RenderHelper.rounded_rect(matrices, (int) center.getX() + 65, (int) center.getY() + 90, (int) center.getX() + 65 + 15, (int) center.getY() + 90 + 45, 7, Color.WHITE);
        RenderHelper.rounded_rect(matrices, (int) center.getX() + 65, (int) center.getY() + 90, (int) center.getX() + 65 + 15, (int) center.getY() + 90 + 45, 7, new Color((int) module.sand_red, (int) module.sand_blue, (int) module.sand_green, (int) module.sand_alpha));
        RenderHelper.rounded_rect(matrices, (int) center.getX() + 65, (int) center.getY() + 90, (int) center.getX() + 65 + 15, (int) center.getY() + 90 + 45, 7, new Color((int) module.sand_red, (int) module.sand_blue, (int) module.sand_green, (int) module.sand_alpha));

        Theme.getSubFont().drawString(matrices, LangHelper.getTranslated("module.fragment_utils.name.explosionboxes"), center.getX() - 50, center.getY() - 70, Theme.getColorText().getRGB());
        Theme.getButtonFont().drawString(matrices, LangHelper.getTranslated("menu.fragment_utils.option.alive_time"), center.getX() - 75, center.getY() - 45, Theme.getColorText().getRGB());
        Theme.getButtonFont().drawString(matrices, LangHelper.getTranslated("menu.fragment_utils.option.track_sand"), center.getX() - 55, center.getY() + 30, Theme.getColorText().getRGB());
        Theme.getButtonFont().drawString(matrices, LangHelper.getTranslated("menu.fragment_utils.option.track_tnt"), center.getX() - 55, center.getY() + 55, Theme.getColorText().getRGB());
        Theme.getButtonFont().drawString(matrices, LangHelper.getTranslated("menu.fragment_utils.option.tnt_color"), center.getX() - 75, center.getY() + 75, Theme.getColorText().getRGB());
        Theme.getButtonFont().drawString(matrices, LangHelper.getTranslated("menu.fragment_utils.option.sand_color"), center.getX() - 75, center.getY() + 100, Theme.getColorText().getRGB());

        super.render(matrices, mouseX, mouseY, delta);
    }
}
