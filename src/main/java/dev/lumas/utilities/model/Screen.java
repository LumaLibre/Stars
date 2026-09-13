package dev.lumas.utilities.model;

import dev.lumas.utilities.util.CommandInput;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * A screen applies a mask onto a specific command and filters out
 * any characters that aren't in the allowedChars string, as well as
 * any blocked character sequences.
 */
@Getter
@NullMarked
public class Screen {

    public static final String BYPASS_PERMISSION = "stars.bypass-screen";

    private final List<String> commandNames;
    private final String allowedChars;
    private final List<String> blockedSequences;

    private final @Nullable Pattern blockedPattern;

    public Screen(List<String> commandNames, String allowedChars, List<String> blockedSequences) {
        this.commandNames = commandNames;
        this.allowedChars = allowedChars;
        this.blockedSequences = blockedSequences;

        String alternation = blockedSequences.stream()
                .filter(sequence -> !sequence.isEmpty())
                .map(Pattern::quote)
                .collect(Collectors.joining("|"));
        this.blockedPattern = alternation.isEmpty() ? null : Pattern.compile(alternation, Pattern.CASE_INSENSITIVE);
    }


    public boolean matches(String inputCommand) {
        return CommandInput.matchesAny(inputCommand, this.commandNames);
    }

    public String filter(String inputCommand) {
        String filtered = inputCommand;
        if (!this.allowedChars.isEmpty()) {
            StringBuilder filteredCommand = new StringBuilder("/");
            for (char c : inputCommand.toCharArray()) {
                if (this.allowedChars.indexOf(c) != -1) {
                    filteredCommand.append(c);
                }
            }
            filtered = filteredCommand.toString();
        }

        if (this.blockedPattern == null) {
            return filtered;
        }
        // Repeat so removals can't join into a new blocked sequence (e.g. "&&ii" -> "&i")
        String previous;
        do {
            previous = filtered;
            filtered = this.blockedPattern.matcher(filtered).replaceAll("");
        } while (!filtered.equals(previous));
        return filtered;
    }
}
