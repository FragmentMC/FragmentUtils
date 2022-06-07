package stanuwu.fragmentutils.gui.component;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import org.lwjgl.glfw.GLFW;
import stanuwu.fragmentutils.Utils.ColorHelper;
import stanuwu.fragmentutils.Utils.StringHelper;
import stanuwu.fragmentutils.gui.Theme;
import stanuwu.fragmentutils.render.RenderHelper;
import stanuwu.fragmentutils.render.font.TTFFontRenderer;

import java.util.function.Consumer;

public class RoundedTextbox extends Clickable implements Typeable, Draggable, Focusable {
    boolean isFocused;
    public String content;
    int lineticks;
    int pos;
    int blinkspeed;
    int anchorpos;
    int selectpos;
    boolean setpos;

    protected TTFFontRenderer font = Theme.getButtonFont();

    public RoundedTextbox(int width, int heigth, int x, int y, Consumer<Object> onClick, String content) {
        super(width, heigth, x, y, onClick);
        this.content = content;
        this.isFocused = false;
        this.lineticks = 0;
        this.pos = 0;
        this.anchorpos = 0;
        this.selectpos = 0;
        this.blinkspeed = 40;
        this.setpos = false;
    }

    @Override
    public void render(MatrixStack poseStack, int mouseX, int mouseY, ComponentGroup componentGroup) {
        Vector2f center = componentGroup.getCenter();
        mouseX -= center.getX();
        mouseY -= center.getY();
        RenderHelper.rounded_rect(poseStack, this.x, this.y, this.x + width, this.y + height, 7, isFocused ? Theme.getColorHover() : Theme.getColorPrimary());
        drawText(poseStack, mouseX, mouseY, componentGroup);
        if (isDragging.get()) {
            drag(mouseX + center.getX(), mouseY, componentGroup);
        }
    }

    void drawText(MatrixStack poseStack, int mouseX, int mouseY, ComponentGroup componentGroup) {
        float textX = getTextX();
        float cursorPos = textX + this.lengthAtPos(this.pos) - 1;
        float textY = this.y + (float) this.height / 2 - font.getHeight(this.content) / 2 + 1;

        if (cursorPos < this.x) {
            anchorpos--;
        } else if (cursorPos > (this.x + this.width - 7)) {
            anchorpos++;
        }

        Vector2f center = componentGroup.getCenter();
        RenderHelper.scaledScissor(x + center.getX() + 2, y + center.getY(), width - 2, height);

        if (selectpos != pos && this.isFocused) {
            float posLength = lengthAtPos(pos) + textX;
            float selectLength = lengthAtPos(selectpos) + textX;
            float sx1 = Math.min(posLength, selectLength);
            float sx2 = sx1 == posLength ? selectLength : posLength;
            RenderHelper.rect(poseStack, sx1 - 2, textY, sx2, textY + font.getHeight(this.content), ColorHelper.invert(Theme.getColorText()));
        }

        font.drawString(poseStack, content, textX, textY, Theme.getColorText().getRGB());
        if (this.setpos) {
            this.setpos = false;
            setPosOnClick(mouseX, textX, false);
        }
        if (this.isFocused) {
            renderLine(poseStack, textX, textY, cursorPos);
        }

        RenderSystem.disableScissor();
    }

    void setPosOnClick(int mouseX, float textX, boolean dragging) {
        if (mouseX > font.getWidth(this.content) + textX) {
            this.pos = this.content.length();
            if (!dragging) {
                this.selectpos = pos;
            }
        } else {
            for (int i = 0; i < this.content.length() + 1; i++) {
                if (mouseX < lengthAtPos(i) + textX) {
                    this.pos = i;
                    if (!dragging) {
                        this.selectpos = pos;
                    }
                    break;
                }
            }
        }
    }

    float lengthAtPos(int pos) {
        return this.content.length() > 0 ? font.getWidth(this.content.substring(0, pos)) : 0;
    }

    float getTextX() {
        return this.x + 7 - this.lengthAtPos(anchorpos);
    }

    @Override
    public void drag(double mouseX, double mouseY, ComponentGroup componentGroup) {
        if (this.isFocused) {
            mouseX -= componentGroup.getCenter().getX();
            setPosOnClick((int) mouseX, getTextX(), true);
        }
    }

    @Override
    public void stopDrag() {
        isDragging.set(false);
    }

    @Override
    public void onClick() {
        isDragging.set(true);
        this.isFocused = true;
        this.setpos = true;
    }

    public void sendClick() {
        this.onClick.accept(this.content);
    }

    @Override
    public void unfocus() {
        this.isFocused = false;
        sendClick();
    }

    @Override
    public void onKeypress(int keyCode, int scanCode, int modifiers) {
        if (this.isFocused) {
            // action type
            // key
            switch (keyCode) {
                // backspace
                case GLFW.GLFW_KEY_BACKSPACE:
                    if (!deleteSelected()) pressBackspace();
                    break;

                // left or right
                case GLFW.GLFW_KEY_LEFT:
                    if (pos > 0) {
                        pos--;
                        selectpos = pos;
                    }
                    break;
                case GLFW.GLFW_KEY_RIGHT:
                    if (pos < content.length()) {
                        pos++;
                        selectpos = pos;
                    }
                    break;

                // select all
                case GLFW.GLFW_KEY_A:
                    if (isControlDown(modifiers)) {
                        this.selectpos = 0;
                        this.pos = content.length();
                    }
                    break;

                case GLFW.GLFW_KEY_C:
                    if (isControlDown(modifiers)) {
                        setClipboard(getSelected());
                    }
                    break;

                case GLFW.GLFW_KEY_V:
                    if (isControlDown(modifiers)) {
                        deleteSelected();
                        insertText(getClipboard());
                    }
                    break;

                case GLFW.GLFW_KEY_X:
                    if (isControlDown(modifiers)) {
                        setClipboard(getSelected());
                        deleteSelected();
                    }
                    break;

                case GLFW.GLFW_KEY_ENTER:
                    unfocus();
                    break;
            }
        }
    }


    String getSelected() {
        StringBuilder stringBuilder = new StringBuilder();
        int from = Math.min(pos, selectpos);
        int to = from == pos ? selectpos : pos;
        for (int i = from; i < to; i++) {
            stringBuilder.append(this.content.toCharArray()[i]);
        }
        return new String(stringBuilder);
    }

    String getClipboard() {
        return GLFW.glfwGetClipboardString(0);
    }

    void setClipboard(String text) {
        GLFW.glfwSetClipboardString(0, text);
    }

    boolean isControlDown(int modifiers) {
        return modifiers == GLFW.GLFW_MOD_CONTROL;
    }

    void insertText(String text) {
        if (text != null) {
            deleteSelected();
            this.content = StringHelper.insertAtIndex(this.content, text, pos);
            pos += text.length();
            selectpos = pos;
        }
    }

    boolean deleteSelected() {
        boolean delete = pos != selectpos;
        if (delete) {
            int from = Math.min(pos, selectpos);
            int to = from == pos ? selectpos : pos;
            String c = this.content;
            for (int i = from; i < to; i++) {
                c = StringHelper.removeAtIndex(c, from);
            }
            this.content = c;
            pos = from;
            selectpos = pos;
            anchorpos = Math.max(0, anchorpos - (to - from));
        }
        return delete;
    }

    void pressBackspace() {
        if (pos > 0) {
            content = StringHelper.removeAtIndex(content, pos - 1);
            pos--;
            selectpos = pos;
            if (anchorpos > 0) {
                anchorpos--;
            }
        }
    }

    void renderLine(MatrixStack poseStack, float textX, float textY, float cursorPos) {
        if (this.lineticks > this.blinkspeed) {
            RenderHelper.v_line(poseStack, cursorPos - 1, textY, cursorPos - 1, textY + font.getHeight(this.content), 1, Theme.getColorText());
        }
        this.lineticks = this.lineticks > this.blinkspeed * 2 ? 0 : this.lineticks + 1;
    }

    @Override
    public void charTyped(int chr, int modifiers) {
        if (this.isFocused) {
            insertText(Character.toString(chr));
        }
    }

    public void setContent(String content) {
        pos = 0;
        selectpos = 0;
        anchorpos = 0;
        this.content = content;
    }
}
