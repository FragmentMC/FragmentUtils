package stanuwu.fragmentutils.gui;

import net.minecraft.text.Text;

public class SubScreen extends MenuScreen {
    protected SubScreen(Text title) {
        super(title);
    }

    @Override
    public void close() {
        MenuHandler.openMenu();
    }
}
