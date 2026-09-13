package dev.lumas.utilities.util;

import org.jspecify.annotations.NullMarked;

import java.util.Collection;

@NullMarked
public final class CommandInput {

    private CommandInput() {
    }

    /**
     * Checks whether the label of a raw command message (e.g. "/nick foo")
     * matches any of the given command names, ignoring case.
     */
    public static boolean matchesAny(String inputCommand, Collection<String> commandNames) {
        if (!inputCommand.contains("/")) {
            return false;
        }
        String firstWord = inputCommand.replaceAll("/", "").split(" ")[0];
        for (String commandName : commandNames) {
            if (firstWord.equalsIgnoreCase(commandName)) {
                return true;
            }
        }
        return false;
    }
}
