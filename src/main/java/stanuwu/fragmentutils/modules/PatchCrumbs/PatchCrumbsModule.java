package stanuwu.fragmentutils.modules.PatchCrumbs;

import org.lwjgl.glfw.GLFW;
import stanuwu.fragmentutils.modules.Module;

public class PatchCrumbsModule extends Module {
    boolean path;
    boolean path_sideways;
    boolean tracers;
    boolean sand;
    float minOffset = 0;
    float maxOffset = 10;
    float y_offset;
    float minTime = 1;
    float maxTime = 60;
    float time;
    float minSize = 0.1f;
    float maxSize = 1;
    float size;
    float red;
    float green;
    float blue;
    float alpha;

    public PatchCrumbsModule(boolean enabled, boolean path, boolean path_sideways, boolean tracers, boolean sand, float y_offset, float size, float time, float red, float green, float blue, float alpha) {
        super(enabled,
                "patchcrumbs",
                PatchCrumbsScreen.class,
                GLFW.GLFW_KEY_UNKNOWN);
        this.path = path;
        this.path_sideways = path_sideways;
        this.tracers = tracers;
        this.sand = sand;
        this.y_offset = y_offset;
        this.size = size;
        this.time = time;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public boolean getPath() {
        return path;
    }

    public boolean getPath_sideways() {
        return path_sideways;
    }

    public boolean getTracers() {
        return tracers;
    }

    public boolean getSand() {
        return sand;
    }

    public float getMinOffset() {
        return minOffset;
    }

    public float getMaxOffset() {
        return maxOffset;
    }

    public float getY_offset() {
        return y_offset;
    }

    public float getMinTime() {
        return minTime;
    }

    public float getMaxTime() {
        return maxTime;
    }

    public float getTime() {
        return time;
    }

    public float getMinSize() {
        return minSize;
    }

    public float getMaxSize() {
        return maxSize;
    }

    public float getSize() {
        return size;
    }

    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }

    public float getAlpha() {
        return alpha;
    }
}
