package stanuwu.fragmentutils.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableTextContent;

public class MenuHandler {
    static MinecraftClient client = MinecraftClient.getInstance();

    public static void onMenuKeyPressed() {
        if (client.currentScreen instanceof MenuScreen) {
            client.currentScreen.close();
        } else {
            openMenu();
        }
    }

    public static void openMenu() {
        client.setScreen(new SelectScreen(MutableText.of(new TranslatableTextContent("screen.fragment_utils.menu"))));
    }
}
