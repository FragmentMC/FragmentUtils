package stanuwu.fragmentutils.modules.BreadCrumbs;

import net.minecraft.client.render.BufferBuilder;
import stanuwu.fragmentutils.render.RenderHelper3d;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Objects;

public class BreadCrumbLine {
    double x1;
    double y1;
    double z1;
    double x2;
    double y2;
    double z2;
    int age;
    Color color;

    public BreadCrumbLine(double x1, double y1, double z1, double x2, double y2, double z2, int age, Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
        this.age = age;
        this.color = color;
    }

    public void render(BufferBuilder bufferBuilder) {
        RenderHelper3d.renderLine(bufferBuilder, x1, y1, z1, x2, y2, z2, color);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BreadCrumbLine) {
            BreadCrumbLine breadCrumbLine = (BreadCrumbLine) obj;
            DecimalFormat decimalFormat = new DecimalFormat("#.###");
            return Objects.equals(decimalFormat.format(x1), decimalFormat.format(breadCrumbLine.x1)) &&
                    Objects.equals(decimalFormat.format(y1), decimalFormat.format(breadCrumbLine.y1)) &&
                    Objects.equals(decimalFormat.format(z1), decimalFormat.format(breadCrumbLine.z1)) &&
                    Objects.equals(decimalFormat.format(x2), decimalFormat.format(breadCrumbLine.x1)) &&
                    Objects.equals(decimalFormat.format(y2), decimalFormat.format(breadCrumbLine.y2)) &&
                    Objects.equals(decimalFormat.format(z2), decimalFormat.format(breadCrumbLine.z2)) &&
                    age == breadCrumbLine.age &&
                    color.getRGB() == breadCrumbLine.color.getRGB();
        } else {
            return false;
        }
    }

    public String getKey() {
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        return new String(new StringBuilder()
                .append(decimalFormat.format(x1))
                .append(decimalFormat.format(y1))
                .append(decimalFormat.format(z1))
                .append(decimalFormat.format(x2))
                .append(decimalFormat.format(y2))
                .append(decimalFormat.format(z2))
                .append(color.getRGB())
        );
    }
}
