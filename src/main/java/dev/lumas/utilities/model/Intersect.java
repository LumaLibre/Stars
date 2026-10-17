package dev.lumas.utilities.model;

import dev.lumas.utilities.Stars;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

@Getter
@NullMarked
public class Intersect implements Registerable {

    public static final String PREFER_PERMISSION_PREFIX = "stars.prefer.";

    private static final String FALLBACK_PREFIX = Stars.getInstance().getName().toLowerCase();

    private final String commandName;
    private final List<Candidate> candidates;

    private @Nullable Command registeredCommand;

    public Intersect(String commandName, List<Candidate> candidates) {
        this.commandName = commandName;
        this.candidates = candidates;
    }

    public @Nullable Candidate select(CommandSender sender) {
        Candidate fallback = null;
        for (Candidate candidate : this.candidates) {
            if (candidate.resolve() == null) {
                continue;
            }
            if (sender.hasPermission(PREFER_PERMISSION_PREFIX + candidate.section())) {
                return candidate;
            }
            fallback = candidate;
        }
        return fallback;
    }

    @Override
    public void register() {
        if (this.registeredCommand != null) {
            return;
        }

        this.registeredCommand = new BukkitCommand(this.commandName) {
            @Override
            public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                Candidate candidate = Intersect.this.select(sender);
                Command target = candidate == null ? null : candidate.resolve();
                if (target == null) {
                    return true;
                }
                return target.execute(sender, candidate.label(), args);
            }

            @Override
            public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
                return this.tabComplete(sender, alias, args, null);
            }

            @Override
            public List<String> tabComplete(CommandSender sender, String alias, String[] args, @Nullable Location location) {
                Candidate candidate = Intersect.this.select(sender);
                Command target = candidate == null ? null : candidate.resolve();
                if (target == null || args.length == 0) {
                    return List.of();
                }
                return target.tabComplete(sender, candidate.label(), args, location);
            }
        };

        CommandMap commandMap = Bukkit.getCommandMap();
        // Only the unprefixed label is taken over. The candidates stay registered
        // under their own namespaces, which is what the forwarding resolves.
        if (commandMap.getCommand(this.commandName) != null) {
            Stars.getInstance().getLogger().warning("Overriding existing command: " + this.commandName);
            commandMap.getKnownCommands().remove(this.commandName);
        }
        commandMap.register(this.commandName, FALLBACK_PREFIX, this.registeredCommand);
    }

    @Override
    public void unregister() {
        if (this.registeredCommand == null) {
            return;
        }
        CommandMap commandMap = Bukkit.getCommandMap();
        this.registeredCommand.unregister(commandMap);

        var knownCommands = commandMap.getKnownCommands();
        knownCommands.remove(this.commandName);
        knownCommands.remove(FALLBACK_PREFIX + ":" + this.commandName);
        this.registeredCommand = null;
    }

    public record Candidate(String section, String commandName, String label) {

        public static Candidate of(String section, String command) {
            int namespace = command.indexOf(':');
            if (namespace == -1) {
                return new Candidate(section, section + ":" + command, command);
            }
            return new Candidate(section, command, command.substring(namespace + 1));
        }

        public @Nullable Command resolve() {
            return Bukkit.getCommandMap().getCommand(this.commandName);
        }
    }
}
