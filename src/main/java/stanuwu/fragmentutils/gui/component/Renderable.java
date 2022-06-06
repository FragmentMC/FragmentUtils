package stanuwu.fragmentutils.gui.component;

import net.minecraft.client.util.math.MatrixStack;

public abstract class Renderable {
    int width;
    int height;
    int x;
    int y;

    public Renderable(int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public abstract void render(MatrixStack poseStack, int mouseX, int mouseY, ComponentGroup componentGroup);
}
