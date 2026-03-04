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

    /// Ex. devrait instancier un objet de type {@link RpgCharacter}
    /// ```java
    /// var data = new RpgCharacterData("Villageois", 10 , 0  , 12   , CharacterJob.RPG_CHARACTER, null   , null);
    /// RpgCharacter villageois = data.asRpgCharacter();
    ///
    /// var data2 = new RpgCharacterData(    "Sans métier", 10 , 0  , 12   , null                      , null   , null);
    /// RpgCharacter noJob = data2.asRpgCharacter();
    /// ```
    ///
    /// Ex. devrait instancier un objet de type {@link Mage}
    /// ```java
    /// var data = new RpgCharacterData("Rincevent", 40 , 2  , 25   , CharacterJob.MAGE, 2   , null);
    /// RpgCharacter rincevent = data.asRpgCharacter();
    /// ```
    ///
    /// Ex. devrait instancier un objet de type {@link Warrior}
    /// ```java
    /// var data = new RpgCharacterData("Cohen", 120 , 5  , 50   , CharacterJob.WARRIOR, 10   , null);
    /// RpgCharacter cohen = data.asRpgCharacter();
    /// ```
    ///
    /// Ex. devrait instancier un objet de type {@link Paladin}
    /// ```java
    /// var data = new RpgCharacterData("Lothar", 40 , 3  , 75   , CharacterJob.PALADIN, 5   , 10);
    /// RpgCharacter lothar = data.asRpgCharacter();
    /// ```
    ///
    /// Ex. devrait instancier un objet de type {@link Priest}
    /// ```java
    /// var data = new RpgCharacterData("Elune", 50 , 7  , 0   , CharacterJob.PRIEST, 60   , 100);
    /// RpgCharacter elune = data.asRpgCharacter();
    /// ```
    public RpgCharacter asRpgCharacter() {
        if (job == null) {
            return new RpgCharacter(name, def, money, hp);
        }
        return switch (job) {
            case WARRIOR -> new Warrior(name, def, hp, money, atk);
            case PRIEST -> new Priest(name, def, hp, money, heal);
            case PALADIN -> new Paladin(name, def, hp, money, atk, heal);
            case MAGE -> new Mage(name, def, hp, money, atk);
            case THIEF -> new Thief(name, def, hp, money, atk);
            case TOURIST -> new Tourist(name, def, hp, money);
            case RPG_CHARACTER -> new RpgCharacter(name, def, money, hp);
        };
    }


    public Optional<Healer> asHealer() {
        if (job == null) {
            return Optional.empty();
        }
        return switch (job) {
            case PRIEST -> Optional.of(new Priest(name, def, hp, money, heal));
            case PALADIN -> Optional.of(new Paladin(name, def, hp, money, atk, heal));
            default -> Optional.empty();
        };
    }

    public Optional<Attacker> asAttacker() {
        if (job == null) {
            return Optional.empty();
        }
        return switch (job) {
            case WARRIOR -> Optional.of(new Warrior(name, def, hp, money, atk));
            case PALADIN -> Optional.of(new Paladin(name, def, hp, money, atk, heal));
            case MAGE -> Optional.of(new Mage(name, def, hp, money, atk));
            case THIEF -> Optional.of(new Thief(name, def, hp, money, atk));
            default -> Optional.empty();
        };
    }

    /// Crée une instance de {@link RpgCharacter} en fonction de {@link RpgCharacter::job}
    ///
    /// Ex.
    /// ```java
    ///
    /// ```
//    public RpgCharacter asRpgCharacter() {
//        throw new UnsupportedOperationException("RpgCharacterData::asRpgCharacter() n'est pas encore implémenté");
//    }
//
//
//    public Optional<Healer> asHealer() {
//        throw new UnsupportedOperationException("RpgCharacterData::asHealer() n'est pas encore implémenté");
//    }
//
//    public Optional<Attacker> asAttacker() {
//        throw new UnsupportedOperationException("RpgCharacterData::asAttacker() n'est pas encore implémenté");
//    }

}
