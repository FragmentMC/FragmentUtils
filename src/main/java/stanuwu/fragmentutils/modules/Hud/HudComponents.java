package stanuwu.fragmentutils.modules.Hud;

import com.oroarmor.config.ConfigItem;
import stanuwu.fragmentutils.config.SimpleConfigItem;
import stanuwu.fragmentutils.modules.Hud.component.*;

import java.util.ArrayList;
import java.util.List;

public class HudComponents {
    public static List<HudComponent> components = new ArrayList<>(List.of(
            new HudFragmentLogo(true, 0, 0, 0),
            new HudFragmentText(true, 0.0527, 0, 1),
            new HudCoordinateText(true, 1, 1, 2),
            new HudFacingText(true, 1, 0.9748, 3)
    ));

    public static ArrayList<ConfigItem<?>> genConfig(ArrayList<ConfigItem<?>> initial) {
        components.forEach((comp) -> {
            initial.add(new SimpleConfigItem<Boolean>(genKeyEnabled(comp.getId()), comp.isEnabled()));
            initial.add(new SimpleConfigItem<Double>("component." + comp.getId() + ".x", comp.getX()));
            initial.add(new SimpleConfigItem<Double>("component." + comp.getId() + ".y", comp.getY()));
        });

        return initial;
    }

    public static String genKeyEnabled(int id) {
        return "component." + id + ".enabled";
    }

    public static String genKeyX(int id) {
        return "component." + id + ".x";
    }

    public static String genKeyY(int id) {
        return "component." + id + ".y";
    }
}
