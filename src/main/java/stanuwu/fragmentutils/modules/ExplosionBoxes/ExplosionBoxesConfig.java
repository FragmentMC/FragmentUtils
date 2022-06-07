package stanuwu.fragmentutils.modules.ExplosionBoxes;

import com.oroarmor.config.ConfigItem;
import com.oroarmor.config.ConfigItemGroup;
import stanuwu.fragmentutils.Utils.FloatHelper;
import stanuwu.fragmentutils.config.SimpleConfigItem;

import java.awt.*;
import java.util.List;

public class ExplosionBoxesConfig extends ConfigItemGroup {
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

    public static ExplosionBoxesModule fromConfig() {
        return new ExplosionBoxesModule(
                ExplosionBoxesConfig.on.getValue(),
                FloatHelper.toFloat(ExplosionBoxesConfig.size.getValue()),
                FloatHelper.toFloat(ExplosionBoxesConfig.time.getValue()),
                ExplosionBoxesConfig.blockPosition.getValue(),
                ExplosionBoxesConfig.highlightOrigin.getValue(),
                new Color(
                        ExplosionBoxesConfig.red.getValue(),
                        ExplosionBoxesConfig.green.getValue(),
                        ExplosionBoxesConfig.blue.getValue(),
                        ExplosionBoxesConfig.alpha.getValue()));
    }

    public static void saveConfig(ExplosionBoxesModule module) {
        ExplosionBoxesConfig.on.setValue(module.getEnabled());
        ExplosionBoxesConfig.size.setValue((double) module.getSize());
        ExplosionBoxesConfig.time.setValue((double) module.getTime());
        ExplosionBoxesConfig.blockPosition.setValue(module.getBlockPosition());
        ExplosionBoxesConfig.highlightOrigin.setValue(module.getHighlightOrigin());
        ExplosionBoxesConfig.red.setValue((int) module.getRed());
        ExplosionBoxesConfig.green.setValue((int) module.getGreen());
        ExplosionBoxesConfig.blue.setValue((int) module.getBlue());
        ExplosionBoxesConfig.alpha.setValue((int) module.getAlpha());
    }
}
