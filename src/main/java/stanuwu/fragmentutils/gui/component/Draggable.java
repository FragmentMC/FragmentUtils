package stanuwu.fragmentutils.gui.component;

import java.util.concurrent.atomic.AtomicBoolean;

public interface Draggable {
    AtomicBoolean isDragging = new AtomicBoolean(false);

    default boolean getDragging() {
        return isDragging.get();
    }

    void drag(double mouseX, double mouseY, ComponentGroup componentGroup);

    void stopDrag();
}
