package stanuwu.fragmentutils.modules.Hud;

import com.oroarmor.config.ConfigItem;
import com.oroarmor.config.ConfigItemGroup;
import stanuwu.fragmentutils.config.FragmentUtilsConfig;
import stanuwu.fragmentutils.config.SimpleConfigItem;

import java.util.ArrayList;
import java.util.List;

public class HudConfig extends ConfigItemGroup {
    public static final ConfigItem<Boolean> on = new SimpleConfigItem<>("on", false);

    public HudConfig() {
        super(HudComponents.genConfig(new ArrayList<>(List.of(on))), "hud");
    }

    public static HudModule fromConfig() {
        HudModule module = new HudModule(on.getValue(), HudComponents.components);
        module.hudComponents.forEach((comp) -> {
            FragmentUtilsConfig.hudConfig.getConfigs().forEach((configItem -> {
                if (HudComponents.genKeyEnabled(comp.getId()).equals(configItem.getName())) {
                    comp.setEnabled((Boolean) configItem.getValue());
                } else if (HudComponents.genKeyX(comp.getId()).equals(configItem.getName())) {
                    comp.setX((double) configItem.getValue());
                } else if (HudComponents.genKeyY(comp.getId()).equals(configItem.getName())) {
                    comp.setY((double) configItem.getValue());
                }
            }));
        });
        return module;
    }

    public static void saveConfig(HudModule module) {
        on.setValue(module.getEnabled());
        module.hudComponents.forEach((comp) -> {
            FragmentUtilsConfig.hudConfig.getConfigs().forEach((configItem -> {
                if (HudComponents.genKeyEnabled(comp.getId()).equals(configItem.getName())) {
                    ((ConfigItem<Boolean>) configItem).setValue(comp.isEnabled());
                } else if (HudComponents.genKeyX(comp.getId()).equals(configItem.getName())) {
                    ((ConfigItem<Double>) configItem).setValue(comp.getX());
                } else if (HudComponents.genKeyY(comp.getId()).equals(configItem.getName())) {
                    ((ConfigItem<Double>) configItem).setValue(comp.getY());
                }
            }));
        });
    }
}