package dev.lumas.utilities.model.okaeri;

import dev.lumas.utilities.model.Limiter;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;

import java.util.List;

public class OkaeriLimiter extends OkaeriConfig implements OkaeriConvertable<Limiter> {

    private List<String> commandNames = List.of(
            "nick", "enick", "nickname", "enickname",
            "essentials:nick", "essentials:enick", "essentials:nickname", "essentials:enickname"
    );
    @Comment("Number of arguments after the command label that aren't counted")
    private int ignoredArgs = 1;
    @Comment("The limit only applies when one of these sequences is present (case-insensitive)")
    private List<String> triggers = List.of("&l", "<bold>", "<b>");
    @Comment("Maximum length of the counted text, excluding color codes")
    private int maxLength = 8;
    @Comment("Sent when the limit is exceeded. {limit} is replaced with maxLength")
    private String message = "<red>Bold text can't be longer than {limit} characters.";

    @Override
    public Limiter convert() {
        return new Limiter(commandNames, ignoredArgs, triggers, maxLength, message);
    }
}
