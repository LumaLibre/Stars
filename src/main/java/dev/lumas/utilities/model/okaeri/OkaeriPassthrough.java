package dev.lumas.utilities.model.okaeri;

import dev.lumas.utilities.model.Passthrough;
import eu.okaeri.configs.OkaeriConfig;

import java.util.List;

public class OkaeriPassthrough extends OkaeriConfig implements OkaeriConvertable<Passthrough> {

    private String commandName = "example";
    private List<String> aliases = List.of("example2");

    @Override
    public Passthrough convert() {
        return new Passthrough(commandName, aliases);
    }
}
