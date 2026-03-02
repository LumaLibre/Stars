package dev.lumas.utilities.model;

import lombok.Getter;
import org.jspecify.annotations.NullMarked;

/**
 * A screen applies a mask onto a specific command and filters out
 * any characters that aren't in the allowedChars string.
 */
@Getter
@NullMarked
public class Screen {

    private final String commandName;
    private final String allowedChars;

    public Screen(String commandName, String allowedChars) {
        this.commandName = commandName;
        this.allowedChars = allowedChars;
    }


    public boolean matches(String inputCommand) {
        if (!inputCommand.contains("/")) {
            return false;
        }
        inputCommand = inputCommand.replaceAll("/", "");

        String firstWord = inputCommand.split(" ")[0];
        return firstWord.equalsIgnoreCase(this.commandName);
    }

    public String filter(String inputCommand) {
        StringBuilder filteredCommand = new StringBuilder("/");
        for (char c : inputCommand.toCharArray()) {
            if (this.allowedChars.indexOf(c) != -1) {
                filteredCommand.append(c);
            }
        }
        return filteredCommand.toString();
    }
}
