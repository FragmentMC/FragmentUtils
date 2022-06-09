package stanuwu.fragmentutils.modules;

import stanuwu.fragmentutils.modules.BreadCrumbs.BreadCrumbsConfig;
import stanuwu.fragmentutils.modules.BreadCrumbs.BreadCrumbsModule;
import stanuwu.fragmentutils.modules.ExplosionBoxes.ExplosionBoxesConfig;
import stanuwu.fragmentutils.modules.ExplosionBoxes.ExplosionBoxesModule;
import stanuwu.fragmentutils.modules.PatchCrumbs.PatchCrumbsConfig;
import stanuwu.fragmentutils.modules.PatchCrumbs.PatchCrumbsModule;

import java.util.Collection;
import java.util.HashMap;

public class Modules {
    static HashMap<String, Module> moduleRegistry = new HashMap<>() {
    };

    public static void initModules() {
        moduleRegistry.put("explosionboxes", ExplosionBoxesConfig.fromConfig());
        moduleRegistry.put("patchcrumbs", PatchCrumbsConfig.fromConfig());
        moduleRegistry.put("breadcrumbs", BreadCrumbsConfig.fromConfig());
    }

    public static void saveModules() {
        ExplosionBoxesConfig.saveConfig((ExplosionBoxesModule) moduleRegistry.get("explosionboxes"));
        PatchCrumbsConfig.saveConfig((PatchCrumbsModule) moduleRegistry.get("patchcrumbs"));
        BreadCrumbsConfig.saveConfig((BreadCrumbsModule) moduleRegistry.get("breadcrumbs"));
    }

    public static Module getModule(String key) {
        return moduleRegistry.get(key);
    }

    public static Collection<Module> getAll() {
        return moduleRegistry.values();
    }
}
