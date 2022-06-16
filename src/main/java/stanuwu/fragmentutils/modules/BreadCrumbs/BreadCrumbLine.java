package stanuwu.fragmentutils.modules.BreadCrumbs;

import net.minecraft.client.render.BufferBuilder;
import stanuwu.fragmentutils.render.RenderHelper3d;
import stanuwu.fragmentutils.utils.DoubleHelper;

public class BreadCrumbLine {
    double x1;
    double y1;
    double z1;
    double x2;
    double y2;
    double z2;
    int age;
    int r;
    int g;
    int b;
    int a;

    public BreadCrumbLine(double x1, double y1, double z1, double x2, double y2, double z2, int age, int r, int g, int b, int a) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
        this.age = age;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public void render(BufferBuilder bufferBuilder) {
        RenderHelper3d.renderLine(bufferBuilder, x1, y1, z1, x2, y2, z2, r, g, b, a);
    }

    public long getKey() {
        return DoubleHelper.makeKeyElement(x1) ^
                DoubleHelper.makeKeyElement(y1) << 1 ^
                DoubleHelper.makeKeyElement(z1) << 2 ^
                DoubleHelper.makeKeyElement(x2) << 3 ^
                DoubleHelper.makeKeyElement(y2) << 5 ^
                DoubleHelper.makeKeyElement(z2) << 6;
    }
}
