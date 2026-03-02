package dev.lumas.utilities.model;

import dev.lumas.utilities.Stars;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * A passthrough directly aliases an external command one or
 * more times. Tab completion and other aspects of the command
 * will act as if it were a normal command.
 */
@Getter
@NullMarked
public class Passthrough implements Registerable {

    private static final String FALLBACK_PREFIX = Stars.getInstance().getName().toLowerCase();

    private final @Nullable Command command;
    private final List<String> aliases;


    public Passthrough(String commandName, List<String> aliases) {
        this.command = Bukkit.getCommandMap().getCommand(commandName);
        this.aliases = aliases;
    }

    @Override
    public void register() {
        if (this.command == null) {
            return;
        }

        CommandMap commandMap = Bukkit.getCommandMap();
        for (String alias : this.aliases) {
            commandMap.register(alias, FALLBACK_PREFIX, this.command);
        }
    }

    @Override
    public void unregister() {
        if (this.command == null) {
            return;
        }
        CommandMap commandMap = Bukkit.getCommandMap();
        var knownCommands = commandMap.getKnownCommands();
        for (String alias : this.aliases) {
            knownCommands.remove(alias);
            knownCommands.remove(FALLBACK_PREFIX + ":" + alias);
        }
    }

}
