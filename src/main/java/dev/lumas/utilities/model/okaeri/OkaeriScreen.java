package dev.lumas.utilities.model.okaeri;

import dev.lumas.utilities.model.Screen;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;

import java.util.ArrayList;
import java.util.List;

public class OkaeriScreen extends OkaeriConfig implements OkaeriConvertable<Screen> {

    private String commandName = "setwarp";
    @Comment("Additional command names this screen applies to")
    private List<String> aliases = List.of();
    @Comment("Characters to keep. Leave empty to allow all characters")
    private String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_ ";
    @Comment("Character sequences to remove (case-insensitive), e.g. \"&i\"")
    private List<String> blockedSequences = List.of();

    @Override
    public Screen convert() {
        List<String> commandNames = new ArrayList<>(aliases);
        commandNames.add(commandName);
        return new Screen(commandNames, allowedChars, blockedSequences);
    }
}
