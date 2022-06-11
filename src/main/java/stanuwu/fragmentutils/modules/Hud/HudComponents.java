package stanuwu.fragmentutils.modules.Hud;

import com.oroarmor.config.ConfigItem;
import stanuwu.fragmentutils.config.SimpleConfigItem;
import stanuwu.fragmentutils.modules.Hud.component.*;

import java.util.ArrayList;
import java.util.List;

public class HudComponents {
    public static List<HudComponent> components = new ArrayList<>(List.of(
            new HudFragmentLogo(true, 0, 0, "fragment_logo"),
            new HudTitleText(true, 0.0588, 0, "title"),
            new HudCoordinateText(true, 1, 1, "coordinates"),
            new HudFacingText(true, 1, 0.9748, "facing"),
            new HudFpsText(true, 0, 0.0989, "fps"),
            new HudTpsText(true, 0, 0.1242, "tps"),
            new HudPingText(true, 0, 0.1495, "ping"),
            new HudServerText(false, 0, 0.1748, "server"),
            new HudEntityCountText(false, 0, 0.2063, "entity_count"),
            new HudCustomText(true, 1, 0, "custom_text")
    ));

    public static ArrayList<ConfigItem<?>> genConfig(ArrayList<ConfigItem<?>> initial) {
        components.forEach((comp) -> {
            initial.add(new SimpleConfigItem<>(genKeyEnabled(comp.getId()), comp.isEnabled()));
            initial.add(new SimpleConfigItem<>("component." + comp.getId() + ".x", comp.getX()));
            initial.add(new SimpleConfigItem<>("component." + comp.getId() + ".y", comp.getY()));
        });

        return initial;
    }

    public static String genKeyEnabled(String id) {
        return "component." + id + ".enabled";
    }

    public static String genKeyX(String id) {
        return "component." + id + ".x";
    }

    public static String genKeyY(String id) {
        return "component." + id + ".y";
    }
}
