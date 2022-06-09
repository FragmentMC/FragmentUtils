package stanuwu.fragmentutils.modules.BreadCrumbs;

import org.lwjgl.glfw.GLFW;
import stanuwu.fragmentutils.modules.Module;

public class BreadCrumbsModule extends Module {
    float minTime = 1;
    float maxTime = 120;
    float time;
    boolean tnt;
    boolean sand;
    float tnt_red;
    float tnt_green;
    float tnt_blue;
    float tnt_alpha;
    float sand_red;
    float sand_green;
    float sand_blue;
    float sand_alpha;

    public BreadCrumbsModule(boolean enabled, float time, boolean tnt, boolean sand, float tnt_red, float tnt_green, float tnt_blue, float tnt_alpha, float sand_red, float sand_green, float sand_blue, float sand_alpha) {
        super(enabled, "breadcrumbs", BreadCrumbsScreen.class, GLFW.GLFW_KEY_UNKNOWN);
        this.time = time;
        this.tnt = tnt;
        this.sand = sand;
        this.tnt_red = tnt_red;
        this.tnt_green = tnt_green;
        this.tnt_blue = tnt_blue;
        this.tnt_alpha = tnt_alpha;
        this.sand_red = sand_red;
        this.sand_green = sand_green;
        this.sand_blue = sand_blue;
        this.sand_alpha = sand_alpha;
    }
}
