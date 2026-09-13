package dev.lumas.utilities.util;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jspecify.annotations.NullMarked;

import java.util.regex.Pattern;

@NullMarked
public final class ColorCodes {

    // &#rrggbb, &x&r&r&g&g&b&b and &0-9/a-f/k-o/r (also with §)
    private static final Pattern LEGACY = Pattern.compile("(?i)[&§](?:#[0-9a-f]{6}|x(?:[&§][0-9a-f]){6}|[0-9a-fk-or])");

    private ColorCodes() {
    }

    /**
     * Removes legacy color codes and MiniMessage tags from the given text.
     */
    public static String strip(String text) {
        String withoutLegacy = LEGACY.matcher(text).replaceAll("");
        return MiniMessage.miniMessage().stripTags(withoutLegacy);
    }
}
