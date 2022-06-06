package stanuwu.fragmentutils.Utils;

public class StringHelper {
    public static String removeAtIndex(String string, int index) {
        if (index < string.length() && index >= 0) {
            StringBuilder stringBuilder = new StringBuilder(string);
            stringBuilder.deleteCharAt(index);
            return new String(stringBuilder);
        } else {
            throw new StringIndexOutOfBoundsException(outOfBounds(index, string));
        }
    }

    public static String insertAtIndex(String string, String insert, int index) {
        if (index < string.length() && index >= 0) {
            StringBuilder stringBuilder = new StringBuilder(string);
            stringBuilder.insert(index, insert);
            return new String(stringBuilder);
        } else if (index == string.length()) {
            return string + insert;
        } else {
            throw new StringIndexOutOfBoundsException(outOfBounds(index, string));
        }
    }

    static String outOfBounds(int index, String string) {
        return "Index " + index + " out of bounds for string \"" + string + "\"";
    }
}
