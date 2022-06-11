package stanuwu.fragmentutils.modules.Hud;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import stanuwu.fragmentutils.Utils.FloatHelper;
import stanuwu.fragmentutils.Utils.LangHelper;
import stanuwu.fragmentutils.gui.SubScreen;
import stanuwu.fragmentutils.gui.Theme;
import stanuwu.fragmentutils.gui.component.RoundedSwitch;
import stanuwu.fragmentutils.gui.component.RoundedTextbox;
import stanuwu.fragmentutils.gui.component.RoundedTextboxSmall;
import stanuwu.fragmentutils.modules.Modules;
import stanuwu.fragmentutils.render.RenderHelper;

import java.awt.*;

public class HudScreen extends SubScreen {
    HudModule module = (HudModule) Modules.getModule("hud");

    final RoundedTextbox redPrimaryBox = new RoundedTextboxSmall(30, 15, -75, 40, this::acceptPrimaryRed, noDecimalString(this.module.primary_red));
    final RoundedTextbox greenPrimaryBox = new RoundedTextboxSmall(30, 15, -40, 40, this::acceptPrimaryGreen, noDecimalString(this.module.primary_green));
    final RoundedTextbox bluePrimaryBox = new RoundedTextboxSmall(30, 15, -5, 40, this::acceptPrimaryBlue, noDecimalString(this.module.primary_blue));

    final RoundedTextbox redSecondaryBox = new RoundedTextboxSmall(30, 15, -75, 75, this::acceptSecondaryRed, noDecimalString(this.module.secondary_red));
    final RoundedTextbox greenSecondaryBox = new RoundedTextboxSmall(30, 15, -40, 75, this::acceptSecondaryGreen, noDecimalString(this.module.secondary_green));
    final RoundedTextbox blueSecondaryBox = new RoundedTextboxSmall(30, 15, -5, 75, this::acceptSecondaryBlue, noDecimalString(this.module.secondary_blue));

    public HudScreen(Text title) {
        super(title);

        components.add(new RoundedSwitch(20, 15, -35, -70, (toggle) -> {
            this.module.toggle();
        }, this.module.getEnabled()));

        components.add(redPrimaryBox);
        components.add(greenPrimaryBox);
        components.add(bluePrimaryBox);
        components.add(redSecondaryBox);
        components.add(greenSecondaryBox);
        components.add(blueSecondaryBox);

        components.add(new RoundedTextboxSmall(
                120,
                15,
                -75,
                -30,
                (textboxValue) -> module.customTitle = (String) textboxValue,
                module.customTitle
        ));

        components.add(new RoundedTextboxSmall(
                120,
                15,
                -75,
                5,
                (textboxValue) -> module.customText = (String) textboxValue,
                module.customText
        ));
    }

    String noDecimalString(float num) {
        return String.format("%d", (int) num);
    }

    private void acceptPrimaryRed(Object textboxValue) {
        float res = prepColor((String) textboxValue);
        this.module.primary_red = res;
        redPrimaryBox.setContent(noDecimalString(res));
    }

    private void acceptPrimaryGreen(Object textboxValue) {
        float res = prepColor((String) textboxValue);
        this.module.primary_green = res;
        greenPrimaryBox.setContent(noDecimalString(res));
    }

    private void acceptPrimaryBlue(Object textboxValue) {
        float res = prepColor((String) textboxValue);
        this.module.primary_blue = res;
        bluePrimaryBox.setContent(noDecimalString(res));
    }

    private void acceptSecondaryRed(Object textboxValue) {
        float res = prepColor((String) textboxValue);
        this.module.secondary_red = res;
        redSecondaryBox.setContent(noDecimalString(res));
    }

    private void acceptSecondaryGreen(Object textboxValue) {
        float res = prepColor((String) textboxValue);
        this.module.secondary_green = res;
        greenSecondaryBox.setContent(noDecimalString(res));
    }

    private void acceptSecondaryBlue(Object textboxValue) {
        float res = prepColor((String) textboxValue);
        this.module.secondary_blue = res;
        blueSecondaryBox.setContent(noDecimalString(res));
    }

    float prepColor(String text) {
        float res = FloatHelper.parseFloatSafe(text);
        res = MathHelper.clamp(res, 0, 255);
        return Math.round(res);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        preRender(matrices);

        Vector2f center = components.getCenter();

        fillGradient(matrices, 0, 0, scaled(width), scaled(height), Theme.getColorBackground().getRGB(), Theme.getColorBackgroundSecondary().getRGB());

        RenderHelper.rounded_rect(matrices, (int) center.getX() - 95, (int) center.getY() - 85, (int) center.getX() + 95, (int) center.getY() + 110, 7, Theme.getColorSecondary());
        RenderHelper.rounded_rect(matrices, (int) center.getX() - 95 + 5, (int) center.getY() - 85 + 5, (int) center.getX() + 95 - 5, (int) center.getY() + 110 - 5, 5, Theme.getColorTertiary());

        Theme.getSubFont().drawString(matrices, LangHelper.getTranslated("module.fragment_utils.name.hud"), center.getX() - 10, center.getY() - 70, Theme.getColorText().getRGB());

        Theme.getButtonFont().drawString(matrices, LangHelper.getTranslated("menu.fragment_utils.option.custom_title"), center.getX() - 75, center.getY() - 45, Theme.getColorText().getRGB());
        Theme.getButtonFont().drawString(matrices, LangHelper.getTranslated("menu.fragment_utils.option.custom_text"), center.getX() - 75, center.getY() - 10, Theme.getColorText().getRGB());

        Theme.getButtonFont().drawString(matrices, LangHelper.getTranslated("menu.fragment_utils.option.primary_color"), center.getX() - 75, center.getY() + 25, Theme.getColorText().getRGB());
        Theme.getButtonFont().drawString(matrices, LangHelper.getTranslated("menu.fragment_utils.option.secondary_color"), center.getX() - 75, center.getY() + 60, Theme.getColorText().getRGB());

        Theme.getDescFont().drawString(matrices, LangHelper.getTranslated("menu.fragment_utils.text.example"), (int) center.getX() + 30, (int) center.getY() + 40 + 4, new Color((int) module.primary_red, (int) module.primary_green, (int) module.primary_blue).getRGB());
        Theme.getDescFont().drawString(matrices, LangHelper.getTranslated("menu.fragment_utils.text.example"), (int) center.getX() + 30, (int) center.getY() + 75 + 4, new Color((int) module.secondary_red, (int) module.secondary_green, (int) module.secondary_blue).getRGB());

        super.render(matrices, mouseX, mouseY, delta);
    }
}
