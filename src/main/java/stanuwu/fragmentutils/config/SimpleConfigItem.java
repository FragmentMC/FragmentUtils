package stanuwu.fragmentutils.config;

import com.oroarmor.config.ConfigItem;

public class SimpleConfigItem<T> extends ConfigItem<T> {
    public SimpleConfigItem(String name, T defaultValue) {
        super(name, defaultValue, "");
    }
}
