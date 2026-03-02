package dev.lumas.utilities.model.okaeri;

import dev.lumas.utilities.model.Screen;
import eu.okaeri.configs.OkaeriConfig;

public class OkaeriScreen extends OkaeriConfig implements OkaeriConvertable<Screen> {

    private String commandName = "setwarp";
    private String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_ ";

    @Override
    public Screen convert() {
        return new Screen(commandName, allowedChars);
    }
}
