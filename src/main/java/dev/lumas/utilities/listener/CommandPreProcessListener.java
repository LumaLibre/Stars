package dev.lumas.utilities.listener;

import dev.lumas.lumacore.manager.modules.AutoRegister;
import dev.lumas.lumacore.manager.modules.RegisterType;
import dev.lumas.lumacore.utility.Text;
import dev.lumas.utilities.manager.ModelManager;
import dev.lumas.utilities.model.Limiter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

@AutoRegister(RegisterType.LISTENER)
public class CommandPreProcessListener implements Listener {

    @EventHandler
    public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String screened = ModelManager.INSTANCE.doFilter(player, event.getMessage());
        if (screened != null) {
            event.setMessage(screened);
        }

        Limiter limiter = ModelManager.INSTANCE.findExceededLimiter(event.getMessage());
        if (limiter != null) {
            event.setCancelled(true);
            Text.msg(player, limiter.getMessage().replace("{limit}", String.valueOf(limiter.getMaxLength())));
        }
    }
}
