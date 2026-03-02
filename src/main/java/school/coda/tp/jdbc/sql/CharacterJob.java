package school.coda.tp.jdbc.sql;

public enum CharacterJob {
    WARRIOR("Warrior"),
    PRIEST("Priest"),
    PALADIN("Paladin"),
    MAGE("Mage"),
    THIEF("Thief"),
    TOURIST("Tourjst"),
    RPG_CHARACTER(null);

    public String jobName;

    CharacterJob(String jobName) {
        this.jobName = jobName;
    }

    public static CharacterJob fromName(String name) {
        for (CharacterJob characterJob : values()) {
            if (characterJob.jobName.equals(name)) {
                return characterJob;
            }
        }
        return RPG_CHARACTER;
    }
}
