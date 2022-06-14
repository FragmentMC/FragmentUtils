package stanuwu.fragmentutils.modules.PatchCrumbs;

import com.oroarmor.config.ConfigItem;
import com.oroarmor.config.ConfigItemGroup;
import stanuwu.fragmentutils.utils.FloatHelper;
import stanuwu.fragmentutils.config.SimpleConfigItem;

import java.util.List;

public class PatchCrumbsConfig extends ConfigItemGroup {
    public static final ConfigItem<Boolean> on = new SimpleConfigItem<>("on", false);
    public static final ConfigItem<Boolean> path = new SimpleConfigItem<>("path", true);
    public static final ConfigItem<Boolean> path_sideways = new SimpleConfigItem<>("path_sideways", true);
    public static final ConfigItem<Boolean> tracers = new SimpleConfigItem<>("tracers", false);
    public static final ConfigItem<Boolean> sand = new SimpleConfigItem<>("sand", true);
    public static final ConfigItem<Double> y_offset = new SimpleConfigItem<>("y_offset", 0d);
    public static final ConfigItem<Double> size = new SimpleConfigItem<>("size", 1d);
    public static final ConfigItem<Double> time = new SimpleConfigItem<>("time", 10d);
    public static final ConfigItem<Integer> red = new SimpleConfigItem<>("color.red", 255);
    public static final ConfigItem<Integer> green = new SimpleConfigItem<>("color.green", 0);
    public static final ConfigItem<Integer> blue = new SimpleConfigItem<>("color.blue", 255);
    public static final ConfigItem<Integer> alpha = new SimpleConfigItem<>("color.alpha", 255);

    public PatchCrumbsConfig() {
        super(List.of(on, path, path_sideways, tracers, sand, y_offset, size, time, red, green, blue, alpha), "patchcrumbs");
    }

    public static PatchCrumbsModule fromConfig() {
        return new PatchCrumbsModule(
                on.getValue(),
                path.getValue(),
                path_sideways.getValue(),
                tracers.getValue(),
                sand.getValue(),
                FloatHelper.toFloat(y_offset.getValue()),
                FloatHelper.toFloat(size.getValue()),
                FloatHelper.toFloat(time.getValue()),
                red.getValue(),
                green.getValue(),
                blue.getValue(),
                alpha.getValue());
    }

    public static void saveConfig(PatchCrumbsModule module) {
        on.setValue(module.getEnabled());
        path.setValue(module.getPath());
        path_sideways.setValue(module.getPath_sideways());
        tracers.setValue(module.getTracers());
        sand.setValue(module.getSand());
        y_offset.setValue((double) module.getY_offset());
        size.setValue((double) module.getSize());
        time.setValue((double) module.getTime());
        red.setValue((int) module.getRed());
        green.setValue((int) module.getGreen());
        blue.setValue((int) module.getBlue());
        alpha.setValue((int) module.getAlpha());
    }
}
