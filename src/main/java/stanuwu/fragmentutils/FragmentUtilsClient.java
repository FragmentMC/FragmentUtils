package stanuwu.fragmentutils;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.MinecraftClient;
import stanuwu.fragmentutils.config.FragmentUtilsConfig;
import stanuwu.fragmentutils.donor.DonorCapes;
import stanuwu.fragmentutils.keybinds.Keybinds;
import stanuwu.fragmentutils.modules.Modules;
import stanuwu.fragmentutils.render.font.Fonts;
import stanuwu.fragmentutils.trackers.Trackers;

public class FragmentUtilsClient implements ClientModInitializer {
    public static FragmentUtilsConfig conf;

    @Override
    public void onInitializeClient() {
        //initialize config
        conf = new FragmentUtilsConfig();
        conf.readConfigFromFile();

        //initialize trackers
        Trackers.init();

        //initialize modules
        Modules.initModules();

        //initialize keybindings
        Keybinds.init();

        //initialize cape loading
        DonorCapes.init();

        //register an event for the on loaded function
        ClientLifecycleEvents.CLIENT_STARTED.register(this::onLoadedClient);
        ClientLifecycleEvents.CLIENT_STOPPING.register(this::onStoppingClient);
    }

    private void onStoppingClient(MinecraftClient minecraftClient) {
        Modules.saveModules();
        conf.saveConfigToFile();
    }

    public void onLoadedClient(MinecraftClient listener) {
        //register event to initialize fonts once client is loaded
        Fonts.init();
    }
}
