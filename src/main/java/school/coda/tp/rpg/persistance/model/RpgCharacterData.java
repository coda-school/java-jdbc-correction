package school.coda.tp.rpg.persistance.model;

import school.coda.rpg.character.abilities.damage.Attacker;
import school.coda.rpg.character.abilities.healing.Healer;
import school.coda.rpg.character.job.Mage;
import school.coda.rpg.character.job.Paladin;
import school.coda.rpg.character.job.Priest;
import school.coda.rpg.character.job.RpgCharacter;
import school.coda.rpg.character.job.Thief;
import school.coda.rpg.character.job.Tourist;
import school.coda.rpg.character.job.Warrior;

import java.util.Optional;

public record RpgCharacterData(Integer id,
                               String name,
                               int hp,
                               int def,
                               int money,
                               CharacterJob job,
                               Integer atk,
                               Integer heal
) {

    // For inserting in db without id
    public RpgCharacterData(
            String name,
            int hp,
            int def,
            int money,
            CharacterJob job,
            Integer atk,
            Integer heal
    ) {
        this(null,
                name,
                hp,
                def,
                money,
                job, atk,
                heal
        );
    }


    ///  A copy of the character with a new name
    public RpgCharacterData copyUpdatingName(String newName) {
        return new RpgCharacterData(
                this.id(),
                newName,
                this.hp(),
                this.def(),
                this.money(),
                this.job(), this.atk(),
                this.heal()
        );
    }

    public RpgCharacterData copyUpdatingId(int id) {
        return new RpgCharacterData(
                id,
                this.name(),
                this.hp(),
                this.def(),
                this.money(),
                this.job(), this.atk(),
                this.heal()
        );
    }



}
