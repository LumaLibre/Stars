package dev.lumas.utilities;


import dev.lumas.lumacore.manager.modules.ModuleManager;
import dev.lumas.utilities.config.Config;
import dev.lumas.utilities.manager.ModelManager;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.serdes.standard.StandardSerdes;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;

public final class Stars extends JavaPlugin {


    private static @Getter Stars instance;
    private static @Getter Config okaeriConfig;
    private static ModuleManager moduleManager;

    @Override
    public void onEnable() {
        instance = this;
        okaeriConfig = loadConfig(Config.class, "commands.yml");
        moduleManager = new ModuleManager(this);

        moduleManager.reflectivelyRegisterModules();

        Bukkit.getGlobalRegionScheduler().runDelayed(this, t -> {
            ModelManager.INSTANCE.registerAll();
        }, 1);
    }

    @Override
    public void onDisable() {
        ModelManager.INSTANCE.unregisterAll();
        moduleManager.unregisterModules();
    }

    public <T extends OkaeriConfig> T loadConfig(Class<T> configClass, String fileName) {
        Path bindFile = this.getDataPath().resolve(fileName);
        return ConfigManager.create(configClass, it -> {
            it.withConfigurer(new YamlSnakeYamlConfigurer(), new StandardSerdes());
            it.withRemoveOrphans(false);
            it.withBindFile(bindFile);

            it.saveDefaults();
            it.load(true);
        });
    }
}