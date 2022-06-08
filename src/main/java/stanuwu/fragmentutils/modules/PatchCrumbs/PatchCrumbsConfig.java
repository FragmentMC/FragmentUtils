package stanuwu.fragmentutils.modules.PatchCrumbs;

import com.oroarmor.config.ConfigItem;
import com.oroarmor.config.ConfigItemGroup;
import stanuwu.fragmentutils.Utils.FloatHelper;
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
                PatchCrumbsConfig.on.getValue(),
                PatchCrumbsConfig.path.getValue(),
                PatchCrumbsConfig.path_sideways.getValue(),
                PatchCrumbsConfig.tracers.getValue(),
                PatchCrumbsConfig.sand.getValue(),
                FloatHelper.toFloat(PatchCrumbsConfig.y_offset.getValue()),
                FloatHelper.toFloat(PatchCrumbsConfig.size.getValue()),
                FloatHelper.toFloat(PatchCrumbsConfig.time.getValue()),
                PatchCrumbsConfig.red.getValue(),
                PatchCrumbsConfig.green.getValue(),
                PatchCrumbsConfig.blue.getValue(),
                PatchCrumbsConfig.alpha.getValue());
    }

    public static void saveConfig(PatchCrumbsModule module) {
        PatchCrumbsConfig.on.setValue(module.getEnabled());
        PatchCrumbsConfig.path.setValue(module.getPath());
        PatchCrumbsConfig.path_sideways.setValue(module.getPath_sideways());
        PatchCrumbsConfig.tracers.setValue(module.getTracers());
        PatchCrumbsConfig.sand.setValue(module.getSand());
        PatchCrumbsConfig.y_offset.setValue((double) module.getY_offset());
        PatchCrumbsConfig.size.setValue((double) module.getSize());
        PatchCrumbsConfig.time.setValue((double) module.getTime());
        PatchCrumbsConfig.red.setValue((int) module.getRed());
        PatchCrumbsConfig.green.setValue((int) module.getGreen());
        PatchCrumbsConfig.blue.setValue((int) module.getBlue());
        PatchCrumbsConfig.alpha.setValue((int) module.getAlpha());
    }
}
