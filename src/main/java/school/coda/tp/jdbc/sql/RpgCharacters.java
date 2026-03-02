package school.coda.tp.jdbc.sql;

import java.util.List;
import java.util.Optional;

public interface RpgCharacters {

    /**
     * Can throw TechnicalException
     */
    List<RpgCharacterData> all();

    List<RpgCharacterData> allByJob(CharacterJob job);

    Optional<RpgCharacterData> find(int id);

    void delete();

    void update(RpgCharacterData rpgCharacterData);

    void create(RpgCharacterData rpgCharacterData);


}
