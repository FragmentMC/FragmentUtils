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
                on.getValue(),
                FloatHelper.toFloat(size.getValue()),
                FloatHelper.toFloat(time.getValue()),
                blockPosition.getValue(),
                highlightOrigin.getValue(),
                new Color(
                        red.getValue(),
                        green.getValue(),
                        blue.getValue(),
                        alpha.getValue()));
    }

    public static void saveConfig(ExplosionBoxesModule module) {
        on.setValue(module.getEnabled());
        size.setValue((double) module.getSize());
        time.setValue((double) module.getTime());
        blockPosition.setValue(module.getBlockPosition());
        highlightOrigin.setValue(module.getHighlightOrigin());
        red.setValue((int) module.getRed());
        green.setValue((int) module.getGreen());
        blue.setValue((int) module.getBlue());
        alpha.setValue((int) module.getAlpha());
    }
}
