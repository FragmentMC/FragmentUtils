package stanuwu.fragmentutils.Utils;

import java.awt.*;

public class ColorHelper {
    public static Color withAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    public static Color invert(Color color) {
        return new Color(0xFFFFFF - color.getRGB());
    }
}
