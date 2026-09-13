package dev.lumas.utilities.manager;

import dev.lumas.utilities.config.Config;
import dev.lumas.utilities.Stars;
import dev.lumas.utilities.model.Limiter;
import dev.lumas.utilities.model.Registerable;
import dev.lumas.utilities.model.Screen;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class ModelManager<K> {

    public static ModelManager<?> INSTANCE = create();

    private final Set<K> models = ConcurrentHashMap.newKeySet();


    public void add(K model) {
        models.add(model);
    }

    public void registerAll() {
        for (K model : models) {
            if (model instanceof Registerable registerable) {
                registerable.register();
            }
        }
    }

    public void unregisterAll() {
        for (K model : models) {
            if (model instanceof Registerable registerable) {
                registerable.unregister();
            }
        }
    }

    public @Nullable String doFilter(Player player, String inputCommand) {
        if (player.hasPermission(Screen.BYPASS_PERMISSION)) {
            return null;
        }
        for (K model : models) {
            if (model instanceof Screen screen && screen.matches(inputCommand)) {
                return screen.filter(inputCommand);
            }
        }
        return null;
    }

    public @Nullable Limiter findExceededLimiter(String inputCommand) {
        for (K model : models) {
            if (model instanceof Limiter limiter && limiter.matches(inputCommand) && limiter.exceedsLimit(inputCommand)) {
                return limiter;
            }
        }
        return null;
    }


    private static ModelManager<?> create() {
        var newInst = new ModelManager<>();
        Config config = Stars.getOkaeriConfig();
        for (var passthrough : config.getPassthrough()) {
            newInst.add(passthrough.convert());
        }
        for (var octo : config.getOcto()) {
            newInst.add(octo.convert());
        }
        for (var screen : config.getScreen()) {
            newInst.add(screen.convert());
        }
        for (var limiter : config.getLimiter()) {
            newInst.add(limiter.convert());
        }
        return newInst;
    }

    public static void newInstance() {
        INSTANCE = create();
    }
}
