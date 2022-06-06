package stanuwu.fragmentutils.Utils;

import java.util.Set;

public class FloatHelper {
    public static float roundToTwoDecimals(double value) {
        return ((int) ((value + 0.005f) * 100)) / 100f;
    }

    public static float parseFloatSafe(String string) {
        Set<Character> allowed = Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.');
        string = string.replaceAll(",", ".");
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : string.toCharArray()) {
            if (allowed.contains(c)) {
                stringBuilder.append(c);
            }
        }
        return Float.parseFloat(new String(stringBuilder));
    }

    public static Float toFloat(double doubleValue) {
        return (float) doubleValue;
    }
}
