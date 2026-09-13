package dev.lumas.utilities.model;

import dev.lumas.utilities.util.ColorCodes;
import dev.lumas.utilities.util.CommandInput;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * A limiter caps the visible length of a command's text arguments
 * whenever one of its trigger sequences is present. Color codes
 * (legacy and MiniMessage) are not counted.
 */
@Getter
@NullMarked
public class Limiter {

    private final List<String> commandNames;
    private final int ignoredArgs;
    private final List<String> triggers;
    private final int maxLength;
    private final String message;

    public Limiter(List<String> commandNames, int ignoredArgs, List<String> triggers, int maxLength, String message) {
        this.commandNames = commandNames;
        this.ignoredArgs = ignoredArgs;
        this.triggers = triggers;
        this.maxLength = maxLength;
        this.message = message;
    }


    public boolean matches(String inputCommand) {
        return CommandInput.matchesAny(inputCommand, this.commandNames);
    }

    public boolean exceedsLimit(String inputCommand) {
        String[] parts = inputCommand.trim().split(" +");
        // Skip the command label and the ignored arguments
        int start = 1 + Math.max(0, this.ignoredArgs);
        if (parts.length <= start) {
            return false;
        }

        String text = String.join(" ", Arrays.copyOfRange(parts, start, parts.length));
        if (!containsTrigger(text)) {
            return false;
        }

        String stripped = ColorCodes.strip(text);
        return stripped.codePointCount(0, stripped.length()) > this.maxLength;
    }

    private boolean containsTrigger(String text) {
        String lower = text.toLowerCase(Locale.ROOT);
        for (String trigger : this.triggers) {
            if (!trigger.isEmpty() && lower.contains(trigger.toLowerCase(Locale.ROOT))) {
                return true;
            }
        }
        return false;
    }
}
