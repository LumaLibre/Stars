package dev.lumas.utilities.model.okaeri;

import dev.lumas.utilities.model.Octo;
import eu.okaeri.configs.OkaeriConfig;

import java.util.List;

public class OkaeriOcto extends OkaeriConfig implements OkaeriConvertable<Octo> {

    private String commandName = "octo";
    private List<String> executions = List.of("say hello", "say goodbye");

    @Override
    public Octo convert() {
        return new Octo(commandName, executions);
    }
}
