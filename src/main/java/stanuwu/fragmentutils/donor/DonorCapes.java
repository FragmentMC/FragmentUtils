package stanuwu.fragmentutils.donor;

import com.google.gson.Gson;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientLoginNetworkHandler;
import net.minecraft.util.Identifier;
import stanuwu.fragmentutils.events.EventHandler;
import stanuwu.fragmentutils.events.EventType;
import stanuwu.fragmentutils.events.events.Event;
import stanuwu.fragmentutils.events.events.GetCapeTextureEvent;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.UUID;

public class DonorCapes {
    private static final HashMap<UUID, Integer> CAPES = new HashMap<>();

    private static String repo = "https://raw.githubusercontent.com/FragmentMC/FragmentUtilsCapes/master/capes.json";

    private static final Identifier test_cape = new Identifier("fragment_utils", "cape/test_cape.png");
    private static final Identifier lightning_cape = new Identifier("fragment_utils", "cape/lightning_cape.png");
    private static final Identifier magma_cape = new Identifier("fragment_utils", "cape/magma_cape.png");

    public static void init() {
        loadCapes();
        ClientLoginConnectionEvents.INIT.register(DonorCapes::onConnect);
        EventHandler.getInstance().register(EventType.GetCapeTexture, DonorCapes::setDonorCape);
        LivingEntityFeatureRenderEvents.ALLOW_CAPE_RENDER.register(DonorCapes::allowCapeRender);
    }

    private static void onConnect(ClientLoginNetworkHandler clientLoginNetworkHandler, MinecraftClient minecraftClient) {
        loadCapes();
    }

    private static void loadCapes() {
        try {
            URL url = new URL(repo);
            URLConnection request = url.openConnection();
            CapeData[] data = new Gson().fromJson(new InputStreamReader(request.getInputStream()), CapeData[].class);
            for (CapeData c : data) {
                CAPES.putIfAbsent(UUID.fromString(c.uuid), c.cape_key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean allowCapeRender(AbstractClientPlayerEntity abstractClientPlayerEntity) {
        if (CAPES.containsKey(abstractClientPlayerEntity.getUuid())) return true;

        //debug
        //return true;


        return false;
    }

    private static void setDonorCape(Event e) {
        GetCapeTextureEvent event = (GetCapeTextureEvent) e;
        int capeStatus = CAPES.getOrDefault(((GetCapeTextureEvent) e).getUuid(), -1);

        //debug
        //capeStatus = 1;

        switch (capeStatus) {
            case 0:
                event.setTexture(lightning_cape);
                break;
            case 1:
                event.setTexture(magma_cape);
                break;
            case 2:
                break;
            case 3:
                break;
            case 69:
                event.setTexture(test_cape);
                break;
        }
    }
}
