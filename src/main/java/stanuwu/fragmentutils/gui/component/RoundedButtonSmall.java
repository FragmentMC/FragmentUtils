package stanuwu.fragmentutils.gui.component;

import stanuwu.fragmentutils.gui.Theme;

import java.util.function.Consumer;

public class RoundedButtonSmall extends RoundedButton {

    public RoundedButtonSmall(int width, int heigth, int x, int y, Consumer<Object> onClick, String text) {
        super(width, heigth, x, y, onClick, text);
        super.font = Theme.getDescFont();
    }
}
