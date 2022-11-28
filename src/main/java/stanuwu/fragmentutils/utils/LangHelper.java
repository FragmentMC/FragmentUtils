package stanuwu.fragmentutils.utils;

import net.minecraft.text.*;
import net.minecraft.util.Formatting;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class LangHelper {
    public static String getTranslated(String key) {
        return getString(MutableText.of(new TranslatableTextContent(key)));
    }

    public static String getString(MutableText otext) {
        AtomicReference<String> text = new AtomicReference<>("");
        otext.visit((visitor) -> {
            text.set(visitor);
            return Optional.of(visitor);
        });
        return text.get();
    }

    public static MutableText toggleMessageText(String module, boolean toggle) {
        return MutableText.of(new LiteralTextContent(String.format(getTranslated("toggle.fragment_utils.message"), module, toggle)));
    }

    public static String toggleMessage(String module, boolean toggle) {
        return getString(toggleMessageText(module, toggle));
    }

    public static Text booleanColorText(String text, boolean color) {
        return MutableText.of(new LiteralTextContent(text)).setStyle(Style.EMPTY.withColor(color ? Formatting.GREEN : Formatting.RED));
    }
}
