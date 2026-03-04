package school.coda.tp.rpg.persistance.model;

import java.util.Objects;

public enum CharacterJob {
    WARRIOR("Warrior"),
    PRIEST("Priest"),
    PALADIN("Paladin"),
    MAGE("Mage"),
    THIEF("Thief"),
    TOURIST("Tourist"),
    RPG_CHARACTER(null);


    public final String name;

    CharacterJob(String name) {
        this.name = name;
    }

    public static CharacterJob fromName(String name) {

        for (CharacterJob characterJob : values()) {
            if (Objects.equals(characterJob.name, name)) {
                return characterJob;
            }
        }
        return RPG_CHARACTER;
    }
}
