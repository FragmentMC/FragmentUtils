package stanuwu.fragmentutils.gui;

import net.minecraft.util.Identifier;
import stanuwu.fragmentutils.render.font.Fonts;
import stanuwu.fragmentutils.render.font.TTFFontRenderer;

import java.awt.*;

public class Theme {
    private static final Color colorTitle = new Color(255, 255, 0, 255);
    private static final Color colorPrimary = new Color(150, 150, 150, 255);
    private static final Color colorSecondary = new Color(0, 255, 255, 255);
    private static final Color colorTertiary = new Color(100, 100, 100, 255);
    private static final Color colorHover = new Color(135, 135, 135, 255);
    private static final Color colorHoverSecondary = new Color(0, 215, 215, 255);
    private static final Color colorInactive = new Color(100, 100, 100, 255);
    private static final Color colorText = new Color(215, 215, 215, 255);
    private static final Color colorBackground = new Color(0, 0, 0, 150);
    private static final Color colorBackgroundSecondary = new Color(45, 0, 60, 150);
    private static final Color colorOverlay = new Color(0, 0, 0, 30);
    private static final Color colorHudOverlayOff = new Color(255, 0, 0, 100);
    private static final Color colorHudOverlayOn = new Color(0, 255, 0, 100);
    private static final Color colorHudOverlayOffHover = new Color(255, 0, 0, 150);
    private static final Color colorHudOverlayOnHover = new Color(0, 255, 0, 150);
    private static final TTFFontRenderer titleFont = Fonts.fontManager.getFont("Righteous 92");
    private static final TTFFontRenderer hudTitleFont = Fonts.fontManager.getFont("Righteous 64");
    private static final TTFFontRenderer hudSubFont = Fonts.fontManager.getFont("Roboto 24");
    private static final TTFFontRenderer descFont = Fonts.fontManager.getFont("Roboto 20");
    private static final TTFFontRenderer buttonFont = Fonts.fontManager.getFont("Roboto 24");
    private static final TTFFontRenderer subFont = Fonts.fontManager.getFont("Roboto 32");
    private static final Identifier fragmentLogo = new Identifier("fragment_utils", "icon/fragment_r_lowres.png");

    public static Color getColorTitle() {
        return colorTitle;
    }

    public static Color getColorPrimary() {
        return colorPrimary;
    }

    public static Color getColorSecondary() {
        return colorSecondary;
    }

    public static Color getColorTertiary() {
        return colorTertiary;
    }

    public static Color getColorHover() {
        return colorHover;
    }

    public static Color getColorHoverSecondary() {
        return colorHoverSecondary;
    }

    public static Color getColorInactive() {
        return colorInactive;
    }

    public static Color getColorText() {
        return colorText;
    }

    public static Color getColorBackground() {
        return colorBackground;
    }

    public static Color getColorBackgroundSecondary() {
        return colorBackgroundSecondary;
    }

    public static Color getColorOverlay() {
        return colorOverlay;
    }

    public static Color getColorHudOverlayOff() {
        return colorHudOverlayOff;
    }

    public static Color getColorHudOverlayOn() {
        return colorHudOverlayOn;
    }

    public static Color getColorHudOverlayOffHover() {
        return colorHudOverlayOffHover;
    }

    public static Color getColorHudOverlayOnHover() {
        return colorHudOverlayOnHover;
    }

    public static TTFFontRenderer getTitleFont() {
        return titleFont;
    }

    public static TTFFontRenderer getHudTitleFont() {
        return hudTitleFont;
    }

    public static TTFFontRenderer getHudSubFont() {
        return hudSubFont;
    }

    public static TTFFontRenderer getDescFont() {
        return descFont;
    }

    public static TTFFontRenderer getButtonFont() {
        return buttonFont;
    }

    public static TTFFontRenderer getSubFont() {
        return subFont;
    }

    public static Identifier getFragmentLogo() {
        return fragmentLogo;
    }
}
