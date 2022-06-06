package stanuwu.fragmentutils.config;

import com.oroarmor.config.Config;
import com.oroarmor.config.ConfigItem;
import com.oroarmor.config.ConfigItemGroup;

import java.util.List;

public class FragmentUtilsConfig extends Config {
    public static final ConfigItemGroup explosionBoxesConfig = new ExplosionBoxesConfig();

    public static final List<ConfigItemGroup> configs = List.of(explosionBoxesConfig);

    public FragmentUtilsConfig() {
        super(configs, ConfigFile.getFile(), "fragment_utils");
    }

    public static class ExplosionBoxesConfig extends ConfigItemGroup {
        public static final ConfigItem<Boolean> on = new SimpleConfigItem<>("on", false);
        public static final ConfigItem<Double> size = new SimpleConfigItem<>("size", 1d);
        public static final ConfigItem<Double> time = new SimpleConfigItem<>("time", 10d);
        public static final ConfigItem<Boolean> blockPosition = new SimpleConfigItem<>("blockPosition", true);
        public static final ConfigItem<Boolean> highlightOrigin = new SimpleConfigItem<>("highlightOrigin", true);
        public static final ConfigItem<Integer> red = new SimpleConfigItem<>("color.red", 255);
        public static final ConfigItem<Integer> green = new SimpleConfigItem<>("color.green", 0);
        public static final ConfigItem<Integer> blue = new SimpleConfigItem<>("color.blue", 0);
        public static final ConfigItem<Integer> alpha = new SimpleConfigItem<>("color.alpha", 50);

        public ExplosionBoxesConfig() {
            super(List.of(on, size, time, blockPosition, highlightOrigin, red, green, blue, alpha), "explosionboxes");
        }
    }
}
