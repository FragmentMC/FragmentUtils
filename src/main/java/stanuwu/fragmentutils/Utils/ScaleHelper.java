package stanuwu.fragmentutils.Utils;

import net.minecraft.client.MinecraftClient;

public class ScaleHelper {
    public static int scaled(int num) {
        return (int) Math.ceil((MinecraftClient.getInstance().getWindow().getScaleFactor() / 2d) * num);
    }

    public static double scaled(double num) {
        return (MinecraftClient.getInstance().getWindow().getScaleFactor() / 2d) * num;
    }
}
