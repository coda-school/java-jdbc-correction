package school.coda.tp.jdbc.rpg;

import school.coda.rpg.character.job.Mage;
import school.coda.rpg.character.job.Paladin;
import school.coda.rpg.character.job.Priest;
import school.coda.rpg.character.job.RpgCharacter;
import school.coda.rpg.character.job.Thief;
import school.coda.rpg.character.job.Tourist;
import school.coda.rpg.character.job.Warrior;

public class RpgCharacterMapping {

    public static RpgCharacter toRpgCharacter(RpgCharacterData data) {
        return switch (data.job()) {
            case WARRIOR -> new Warrior(data.name(), data.atk(), data.def(), data.money(), data.hp());
            case PRIEST -> new Priest(data.name(), data.def(), data.money(), data.hp(), data.heal());
            case PALADIN -> new Paladin(data.name(), data.def(), data.money(), data.hp(), data.heal(), data.atk());
            case MAGE -> new Mage(data.name(), data.atk(), data.def(), data.money(), data.hp());
            case THIEF -> new Thief(data.name(), data.atk(), data.def(), data.money(), data.hp());
            case TOURIST -> new Tourist(data.name(), data.def(), data.money(), data.hp());
            case RPG_CHARACTER -> new RpgCharacter(data.name(), data.def(), data.money(), data.hp());
        };
    }
}
