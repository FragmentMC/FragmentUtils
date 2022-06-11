package stanuwu.fragmentutils.modules.Hud;

import com.oroarmor.config.ConfigItem;
import com.oroarmor.config.ConfigItemGroup;
import stanuwu.fragmentutils.config.FragmentUtilsConfig;
import stanuwu.fragmentutils.config.SimpleConfigItem;

import java.util.ArrayList;
import java.util.List;

public class HudConfig extends ConfigItemGroup {
    public static final ConfigItem<Boolean> on = new SimpleConfigItem<>("on", false);
    public static final ConfigItem<Integer> primary_red = new SimpleConfigItem<>("color.primary.red", 255);
    public static final ConfigItem<Integer> primary_green = new SimpleConfigItem<>("color.primary.green", 255);
    public static final ConfigItem<Integer> primary_blue = new SimpleConfigItem<>("color.primary.blue", 0);
    public static final ConfigItem<Integer> secondary_red = new SimpleConfigItem<>("color.secondary.red", 215);
    public static final ConfigItem<Integer> secondary_green = new SimpleConfigItem<>("color.secondary.green", 215);
    public static final ConfigItem<Integer> secondary_blue = new SimpleConfigItem<>("color.secondary.blue", 215);
    public static final ConfigItem<String> customTitle = new SimpleConfigItem<>("custom_title", "Fragment Utils");
    public static final ConfigItem<String> customText = new SimpleConfigItem<>("custom_text", "made with <3 by stan");

    public HudConfig() {
        super(HudComponents.genConfig(new ArrayList<>(List.of(on, primary_red, primary_green, primary_blue, secondary_red, secondary_green, secondary_blue, customTitle, customText))), "hud");
    }

    public static HudModule fromConfig() {
        HudModule module = new HudModule(
                on.getValue(),
                HudComponents.components,
                primary_red.getValue(),
                primary_green.getValue(),
                primary_blue.getValue(),
                secondary_red.getValue(),
                secondary_green.getValue(),
                secondary_blue.getValue(),
                customTitle.getValue(),
                customText.getValue());
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
        primary_red.setValue((int) module.primary_red);
        primary_green.setValue((int) module.primary_green);
        primary_blue.setValue((int) module.primary_blue);
        secondary_red.setValue((int) module.secondary_red);
        secondary_green.setValue((int) module.secondary_green);
        secondary_blue.setValue((int) module.secondary_blue);
        customTitle.setValue(module.customTitle);
        customText.setValue(module.customText);
    }
}