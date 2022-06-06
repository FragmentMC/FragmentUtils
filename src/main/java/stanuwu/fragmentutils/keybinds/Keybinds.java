package stanuwu.fragmentutils.keybinds;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import stanuwu.fragmentutils.gui.MenuHandler;
import stanuwu.fragmentutils.modules.Module;
import stanuwu.fragmentutils.modules.Modules;

public class Keybinds {
    private static KeyBinding menuKey;

    public static void init() {
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.fragment_utils.menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.fragment_utils.factions_utils"
        ));

        //register all module keys
        for (Module m : Modules.getAll()) {
            KeyBindingHelper.registerKeyBinding(m.keyBinding);
        }

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (menuKey.wasPressed()) {
                MenuHandler.onMenuKeyPressed();
            }

            //toggle keys for all modules
            for (Module m : Modules.getAll()) {
                if (m.keyBinding.wasPressed()) {
                    m.keyToggle();
                }
            }
        });
    }
}
