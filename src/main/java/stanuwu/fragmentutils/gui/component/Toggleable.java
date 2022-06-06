package stanuwu.fragmentutils.gui.component;

import java.util.function.Consumer;

public abstract class Toggleable extends Clickable {
    public boolean active;

    public Toggleable(int width, int heigth, int x, int y, Consumer<Object> onClick, boolean active) {
        super(width, heigth, x, y, onClick);
        this.active = active;
    }

    @Override
    public void onClick() {
        this.active = !this.active;
        onClick.accept(this);
    }
}
