package stanuwu.fragmentutils.gui.component;

public interface Typeable {
    public void charTyped(int chr, int modifiers);

    public void onKeypress(int keyCode, int scanCode, int modifiers);
}
