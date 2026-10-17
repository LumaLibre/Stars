package dev.lumas.utilities.model.okaeri;

import dev.lumas.utilities.model.Intersect;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OkaeriIntersect extends OkaeriConfig implements OkaeriConvertable<Intersect> {

    @Comment("The shared command name Stars takes over")
    private String commandName = "up";
    @Comment({
            "Each key is a section name, opted into with stars.prefer.<section>.",
            "Each value is the command that section provides; a value without a",
            "namespace is resolved as <section>:<value>. The order matters: the",
            "last section is used for players without any preference permission."
    })
    private Map<String, String> candidates = defaultCandidates();

    @Override
    public Intersect convert() {
        List<Intersect.Candidate> converted = new ArrayList<>();
        this.candidates.forEach((section, command) -> converted.add(Intersect.Candidate.of(section, command)));
        return new Intersect(commandName, converted);
    }

    private static Map<String, String> defaultCandidates() {
        Map<String, String> candidates = new LinkedHashMap<>();
        candidates.put("fawe", "up");
        candidates.put("lumautils", "up");
        return candidates;
    }
}
