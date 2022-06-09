package stanuwu.fragmentutils.modules.BreadCrumbs;

import com.oroarmor.config.ConfigItem;
import com.oroarmor.config.ConfigItemGroup;
import stanuwu.fragmentutils.Utils.FloatHelper;
import stanuwu.fragmentutils.config.SimpleConfigItem;

import java.util.List;

public class BreadCrumbsConfig extends ConfigItemGroup {
    public static final ConfigItem<Boolean> on = new SimpleConfigItem<>("on", false);
    public static final ConfigItem<Double> time = new SimpleConfigItem<>("time", 20d);
    public static final ConfigItem<Boolean> tnt = new SimpleConfigItem<>("tnt", true);
    public static final ConfigItem<Boolean> sand = new SimpleConfigItem<>("sand", true);
    public static final ConfigItem<Integer> tnt_red = new SimpleConfigItem<>("tnt_color.red", 0);
    public static final ConfigItem<Integer> tnt_green = new SimpleConfigItem<>("tnt_color.green", 255);
    public static final ConfigItem<Integer> tnt_blue = new SimpleConfigItem<>("tnt_color.blue", 125);
    public static final ConfigItem<Integer> tnt_alpha = new SimpleConfigItem<>("tnt_color.alpha", 255);
    public static final ConfigItem<Integer> sand_red = new SimpleConfigItem<>("sand_color.red", 255);
    public static final ConfigItem<Integer> sand_green = new SimpleConfigItem<>("sand_color.green", 0);
    public static final ConfigItem<Integer> sand_blue = new SimpleConfigItem<>("sand_color.blue", 255);
    public static final ConfigItem<Integer> sand_alpha = new SimpleConfigItem<>("sand_color.alpha", 255);

    public BreadCrumbsConfig() {
        super(List.of(on, time, tnt, sand, tnt_red, tnt_green, tnt_blue, tnt_alpha, sand_red, sand_green, sand_blue, sand_alpha), "breadcrumbs");
    }

    public static BreadCrumbsModule fromConfig() {
        return new BreadCrumbsModule(
                on.getValue(),
                FloatHelper.toFloat(time.getValue()),
                tnt.getValue(),
                sand.getValue(),
                tnt_red.getValue(),
                tnt_green.getValue(),
                tnt_blue.getValue(),
                tnt_alpha.getValue(),
                sand_red.getValue(),
                sand_green.getValue(),
                sand_blue.getValue(),
                sand_alpha.getValue()
        );
    }

    public static void saveConfig(BreadCrumbsModule module) {
        on.setValue(module.getEnabled());
        time.setValue((double) module.time);
        tnt.setValue(module.tnt);
        sand.setValue(module.sand);
        tnt_red.setValue((int) module.tnt_red);
        tnt_green.setValue((int) module.tnt_green);
        tnt_blue.setValue((int) module.tnt_blue);
        tnt_alpha.setValue((int) module.tnt_alpha);
        sand_red.setValue((int) module.sand_red);
        sand_green.setValue((int) module.sand_green);
        sand_blue.setValue((int) module.sand_blue);
        sand_alpha.setValue((int) module.sand_alpha);
    }
}