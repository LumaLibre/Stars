package dev.lumas.utilities.config;

import dev.lumas.utilities.model.okaeri.OkaeriLimiter;
import dev.lumas.utilities.model.okaeri.OkaeriOcto;
import dev.lumas.utilities.model.okaeri.OkaeriPassthrough;
import dev.lumas.utilities.model.okaeri.OkaeriScreen;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;

import java.util.List;

@Getter
public class Config extends OkaeriConfig {

    @Comment({
            "A passthrough directly aliases an external command one or",
            "more times. Tab completion and other aspects of the command",
            "will act as if it were a normal command."
    })
    private List<OkaeriPassthrough> passthrough = List.of(new OkaeriPassthrough());

    @Comment({
            "An Octo is a command that executes one or more external commands."
    })
    private List<OkaeriOcto> octo = List.of(new OkaeriOcto());

    @Comment({
            "A screen applies a mask onto a specific command and filters out",
            "any characters that aren't in the allowedChars string, as well as",
            "any blockedSequences. Players with stars.bypass-screen are not screened."
    })
    private List<OkaeriScreen> screen = List.of(new OkaeriScreen());

    @Comment({
            "A limiter caps the length of a command's text (excluding color codes)",
            "whenever one of its triggers is present."
    })
    private List<OkaeriLimiter> limiter = List.of(new OkaeriLimiter());
}
