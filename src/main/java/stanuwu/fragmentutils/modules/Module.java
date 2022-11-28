package stanuwu.fragmentutils.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import stanuwu.fragmentutils.gui.SubScreen;
import stanuwu.fragmentutils.utils.LangHelper;

public class Module {
    boolean enabled;
    String key;
    Class<? extends SubScreen> settings;
    public KeyBinding keyBinding;

    public Module(boolean enabled, String key, Class<? extends SubScreen> settings, int hotkey) {
        this.enabled = enabled;
        this.key = key;
        this.settings = settings;
        this.keyBinding = new KeyBinding(
                "key.fragment_utils." + key,
                InputUtil.Type.KEYSYM,
                hotkey,
                "category.fragment_utils.factions_utils"
        );
    }

    public void toggle() {
        enabled = !enabled;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public String getName() {
        return "module.fragment_utils.name." + key;
    }

    public String getDescription() {
        return "module.fragment_utils.desc." + key;
    }

    public void openSettings() {
        try {
            MinecraftClient.getInstance().setScreen(settings.getConstructor(Text.class).newInstance(MutableText.of(new TranslatableTextContent("\"screen.fragment_utils.\" + key"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void keyToggle() {
        this.toggle();
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            player.sendMessage(LangHelper.toggleMessageText(LangHelper.getTranslated(this.getName()), this.getEnabled()), true);
        }
    }
}