package stanuwu.fragmentutils.gui.component;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class ComponentGroup {
    public Screen screen;
    private final List<Clickable> components = new ArrayList<Clickable>();

    public ComponentGroup(Screen screen) {
        this.screen = screen;
    }

    public Vector2f getCenter() {
        return new Vector2f(this.screen.width / 2f, this.screen.height / 2f);
    }

    public int count() {
        return components.size();
    }

    public void add(Clickable component) {
        components.add(component);
        if (component instanceof Initializeable) {
            ((Initializeable) component).init(this);
        }
    }

    public void remove(Clickable component) {
        components.remove(component);
    }

    public void renderAll(MatrixStack poseStack, int mouseX, int mouseY) {
        poseStack.translate(screen.width / 2f, screen.height / 2f, 0);
        for (Clickable component : components) {
            component.render(poseStack, mouseX, mouseY, this);
        }
    }

    public boolean clickAll(int mouseX, int mouseY) {
        boolean clicked = false;
        for (Clickable component : components) {
            if (component.isHovering(mouseX, mouseY, this)) {
                component.onClick();
                clicked = true;
            } else {
                if (component instanceof Focusable) {
                    ((Focusable) component).unfocus();
                }
            }
        }
        return clicked;
    }

    public void dragAll(double mouseX, double mouseY) {
        for (Clickable component : components) {
            if (component instanceof Draggable) {
                if (component.isHovering(mouseX, mouseY, this) && ((Draggable) component).getDragging()) {
                    ((Draggable) component).drag(mouseX, mouseY, this);
                }
            }
        }
    }

    public void stopDragAll() {
        for (Clickable component : components) {
            if (component instanceof Draggable) {
                ((Draggable) component).stopDrag();
            }
        }
    }

    public void keyPressAll(int keyCode, int scanCode, int modifiers) {
        for (Clickable component : components) {
            if (component instanceof Typeable) {
                ((Typeable) component).onKeypress(keyCode, scanCode, modifiers);
            }
        }
    }

    public void charTypeAll(int chr, int modifiers) {
        for (Clickable component : components) {
            if (component instanceof Typeable) {
                ((Typeable) component).charTyped(chr, modifiers);
            }
        }
    }
}
