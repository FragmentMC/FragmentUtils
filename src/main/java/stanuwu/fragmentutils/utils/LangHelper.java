package stanuwu.fragmentutils.utils;

import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class LangHelper {
    public static String getTranslated(String key) {
        return getString(new TranslatableText(key));
    }

    public static String getString(TranslatableText otext) {
        AtomicReference<String> text = new AtomicReference<>("");
        otext.visitSelf((visitor) -> {
            text.set(visitor);
            return Optional.of(visitor);
        });
        return text.get();
    }

    public static TranslatableText toggleMessageText(String module, boolean toggle) {
        return new TranslatableText("toggle.fragment_utils.message", module, booleanColorText(getTranslated("toggle.fragment_utils." + toggle), toggle));
    }

    public static String toggleMessage(String module, boolean toggle) {
        return getString(toggleMessageText(module, toggle));
    }

    public static Text booleanColorText(String text, boolean color) {
        return new LiteralText(text).setStyle(Style.EMPTY.withColor(color ? Formatting.GREEN : Formatting.RED));
    }
}
