package stanuwu.fragmentutils.gui.component;

import stanuwu.fragmentutils.gui.Theme;

import java.util.function.Consumer;

public class RoundedTextboxSmall extends RoundedTextbox {
    public RoundedTextboxSmall(int width, int heigth, int x, int y, Consumer<Object> onClick, String content) {
        super(width, heigth, x, y, onClick, content);
        super.font = Theme.getDescFont();
    }
}
