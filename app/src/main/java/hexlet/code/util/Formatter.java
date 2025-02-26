package hexlet.code.util;

import java.time.format.DateTimeFormatter;

public class Formatter {

    public static DateTimeFormatter formatterCreatedAt = formatter("dd/MM/yyyy HH:mm");

    public static DateTimeFormatter formatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern);
    }
}
