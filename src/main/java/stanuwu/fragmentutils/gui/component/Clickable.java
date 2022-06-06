package stanuwu.fragmentutils.gui.component;

import net.minecraft.client.util.math.Vector2f;

import java.util.function.Consumer;

public abstract class Clickable extends Renderable {
    protected Consumer<Object> onClick;

    public Clickable(int width, int heigth, int x, int y, Consumer<Object> onClick) {
        super(width, heigth, x, y);
        this.onClick = onClick;
    }

    public void onClick() {
        onClick.accept(this.toString());
    }

    public boolean isHovering(double mouseX, double mouseY, ComponentGroup componentGroup) {
        Vector2f center = componentGroup.getCenter();
        mouseX -= center.getX();
        mouseY -= center.getY();
        return (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height);
    }
}
