package dev.lumas.utilities.commands;

import dev.lumas.lumacore.manager.commands.AbstractCommand;
import dev.lumas.lumacore.manager.commands.CommandInfo;
import dev.lumas.lumacore.manager.modules.AutoRegister;
import dev.lumas.lumacore.manager.modules.RegisterType;
import dev.lumas.lumacore.utility.Text;
import dev.lumas.utilities.Stars;
import dev.lumas.utilities.manager.ModelManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@AutoRegister(RegisterType.COMMAND)
@CommandInfo(
        name = "stars",
        permission = "stars.command",
        description = "Reloads this plugin"
)
public class StarsCommand extends AbstractCommand {
    @Override
    public boolean handle(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        long startTime = System.currentTimeMillis();
        Bukkit.getGlobalRegionScheduler().run(Stars.getInstance(), t -> {
            Stars.getOkaeriConfig().load(true);
            ModelManager.INSTANCE.unregisterAll();
            ModelManager.newInstance();
            ModelManager.INSTANCE.registerAll();
            Text.msg(sender, "Done! <gold>(" + (System.currentTimeMillis() - startTime) + "ms</gold>)");
        });
        return true;
    }

    @Override
    public @Nullable List<String> handleTabComplete(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}
