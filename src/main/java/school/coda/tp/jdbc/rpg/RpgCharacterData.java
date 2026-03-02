package school.coda.tp.jdbc.rpg;

public record RpgCharacterData(
        int id,
        String name,
        int hp,
        int def,
        int money,
        int atk,
        int heal,
        CharacterJob job
) {
}
