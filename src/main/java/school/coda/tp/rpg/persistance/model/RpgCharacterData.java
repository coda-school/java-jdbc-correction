package school.coda.tp.rpg.persistance.model;

public record RpgCharacterData(Integer id,
                               String name,
                               int hp,
                               int def,
                               int money,
                               Integer atk,
                               Integer heal,
                               CharacterJob job) {

    // For inserting in db without id
    public RpgCharacterData(
            String name,
            int hp,
            int def,
            int money,
            Integer atk,
            Integer heal,
            CharacterJob job
    ) {
        this(null,
                name,
                hp,
                def,
                money,
                atk,
                heal,
                job);
    }


    ///  A copy of the character with a new name
    public RpgCharacterData copyUpdatingName(String newName) {
        return new RpgCharacterData(
                this.id(),
                newName,
                this.hp(),
                this.def(),
                this.money(),
                this.atk(),
                this.heal(),
                this.job()
        );
    }

    public RpgCharacterData copyUpdatingId(int id) {
        return new RpgCharacterData(
                id,
                this.name(),
                this.hp(),
                this.def(),
                this.money(),
                this.atk(),
                this.heal(),
                this.job()
        );
    }
}
