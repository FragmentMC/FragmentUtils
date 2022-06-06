package stanuwu.fragmentutils.modules;

import stanuwu.fragmentutils.Utils.FloatHelper;
import stanuwu.fragmentutils.config.FragmentUtilsConfig;
import stanuwu.fragmentutils.modules.ExplosionBoxes.ExplosionBoxesModule;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;

public class Modules {
    static HashMap<String, Module> moduleRegistry = new HashMap<String, Module>() {
    };

    public static void initModules() {
        moduleRegistry.put("explosionboxes", new ExplosionBoxesModule(
                FragmentUtilsConfig.ExplosionBoxesConfig.on.getValue(),
                FloatHelper.toFloat(FragmentUtilsConfig.ExplosionBoxesConfig.size.getValue()),
                FloatHelper.toFloat(FragmentUtilsConfig.ExplosionBoxesConfig.time.getValue()),
                FragmentUtilsConfig.ExplosionBoxesConfig.blockPosition.getValue(),
                FragmentUtilsConfig.ExplosionBoxesConfig.highlightOrigin.getValue(),
                new Color(
                        FragmentUtilsConfig.ExplosionBoxesConfig.red.getValue(),
                        FragmentUtilsConfig.ExplosionBoxesConfig.green.getValue(),
                        FragmentUtilsConfig.ExplosionBoxesConfig.blue.getValue(),
                        FragmentUtilsConfig.ExplosionBoxesConfig.alpha.getValue())));
    }

    public static void saveModules() {
        ExplosionBoxesModule explosionBoxesModule = (ExplosionBoxesModule) moduleRegistry.get("explosionboxes");
        FragmentUtilsConfig.ExplosionBoxesConfig.on.setValue(explosionBoxesModule.getEnabled());
        FragmentUtilsConfig.ExplosionBoxesConfig.size.setValue((double) explosionBoxesModule.getSize());
        FragmentUtilsConfig.ExplosionBoxesConfig.time.setValue((double) explosionBoxesModule.getTime());
        FragmentUtilsConfig.ExplosionBoxesConfig.blockPosition.setValue(explosionBoxesModule.getBlockPosition());
        FragmentUtilsConfig.ExplosionBoxesConfig.highlightOrigin.setValue(explosionBoxesModule.getHighlightOrigin());
        FragmentUtilsConfig.ExplosionBoxesConfig.red.setValue((int) explosionBoxesModule.getRed());
        FragmentUtilsConfig.ExplosionBoxesConfig.green.setValue((int) explosionBoxesModule.getGreen());
        FragmentUtilsConfig.ExplosionBoxesConfig.blue.setValue((int) explosionBoxesModule.getBlue());
        FragmentUtilsConfig.ExplosionBoxesConfig.alpha.setValue((int) explosionBoxesModule.getAlpha());
    }

    public static Module getModule(String key) {
        return moduleRegistry.get(key);
    }

    public static Collection<Module> getAll() {
        return moduleRegistry.values();
    }
}
