package dev.lumas.utilities.model;

import dev.lumas.utilities.Stars;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * An Octo is a command that executes one or more external commands.
 */
@Getter
@NullMarked
public class Octo implements Registerable {

    private static final String FALLBACK_PREFIX = Stars.getInstance().getName().toLowerCase();

    private final String commandName;
    private final List<String> executions;

    private @Nullable Command registeredCommand;

    public Octo(String commandName, List<String> executions) {
        this.commandName = commandName;
        this.executions = executions;
    }

    @Override
    public void register() {
        if (this.registeredCommand != null) {
            return;
        }

        this.registeredCommand = new BukkitCommand(commandName) {
            @Override
            public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                for (String execution : executions) {
                    String processedExecution = execution.replace("{sender}", sender.getName());
                    sender.getServer().dispatchCommand(sender, processedExecution);
                }
                return true;
            }
        };

        CommandMap commandMap = Bukkit.getCommandMap();
        Command existing = commandMap.getCommand(commandName);
        if (existing != null) {
            Stars.getInstance().getLogger().warning("Overriding existing command: " + commandMap);
            existing.unregister(commandMap);
            commandMap.getKnownCommands().remove(commandName);
        }
        commandMap.register(commandName, FALLBACK_PREFIX, this.registeredCommand);
    }

    @Override
    public void unregister() {
        if (this.registeredCommand == null) {
            return;
        }
        this.registeredCommand.unregister(Bukkit.getCommandMap());
        this.registeredCommand = null;
    }
}
