package stanuwu.fragmentutils.config;

import com.oroarmor.config.Config;
import com.oroarmor.config.ConfigItemGroup;
import stanuwu.fragmentutils.modules.BreadCrumbs.BreadCrumbsConfig;
import stanuwu.fragmentutils.modules.ExplosionBoxes.ExplosionBoxesConfig;
import stanuwu.fragmentutils.modules.PatchCrumbs.PatchCrumbsConfig;

import java.util.List;

public class FragmentUtilsConfig extends Config {
    public static final ConfigItemGroup explosionBoxesConfig = new ExplosionBoxesConfig();
    public static final ConfigItemGroup patchCrumbsConfig = new PatchCrumbsConfig();
    public static final ConfigItemGroup breadCrumbsConfig = new BreadCrumbsConfig();

    public static final List<ConfigItemGroup> configs = List.of(explosionBoxesConfig, patchCrumbsConfig, breadCrumbsConfig);

    public FragmentUtilsConfig() {
        super(configs, ConfigFile.getFile(), "fragment_utils");
    }
}
