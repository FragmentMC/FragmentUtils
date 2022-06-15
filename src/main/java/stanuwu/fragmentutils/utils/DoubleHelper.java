package stanuwu.fragmentutils.utils;

public class DoubleHelper {
    public static double roundTo3Decimals(double num) {
        return (long) (num * 1000 + 0.5) / 1000.0;
    }

    public static long makeKeyElement(double num) {
        return Double.doubleToLongBits(DoubleHelper.roundTo3Decimals(num));
    }
}
