package stanuwu.fragmentutils.config;

import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;

public class ConfigFile {
    public static File getFile() {
        File file = new java.io.File(FabricLoader.getInstance().getConfigDir().toFile(), "fragment_utils.json");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
