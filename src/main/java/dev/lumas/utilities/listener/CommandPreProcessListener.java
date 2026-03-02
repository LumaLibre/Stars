package dev.lumas.utilities.listener;

import dev.lumas.lumacore.manager.modules.AutoRegister;
import dev.lumas.lumacore.manager.modules.RegisterType;
import dev.lumas.utilities.manager.ModelManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

@AutoRegister(RegisterType.LISTENER)
public class CommandPreProcessListener implements Listener {

    @EventHandler
    public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {
        String screened = ModelManager.INSTANCE.doFilter(event.getMessage());
        if (screened != null) {
            event.setMessage(screened);
        }
    }
}
