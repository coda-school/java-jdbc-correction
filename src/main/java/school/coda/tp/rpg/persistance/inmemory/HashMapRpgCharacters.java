package school.coda.tp.rpg.persistance.inmemory;

import school.coda.tp.rpg.persistance.RpgCharacters;
import school.coda.tp.rpg.persistance.model.CharacterJob;
import school.coda.tp.rpg.persistance.model.RpgCharacterData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/// Implémentation en mémoire basée sur une hashmap
public class HashMapRpgCharacters implements RpgCharacters {
    private Map<Integer, RpgCharacterData> data = new HashMap<>();

    @Override
    public List<RpgCharacterData> all() {
        return data.values().stream().toList();
    }

    @Override
    public List<RpgCharacterData> allByJob(CharacterJob job) {
        return data.values().stream()
                .filter(characterData -> Objects.equals(characterData.job(), job))
                .toList();
    }

    @Override
    public Optional<RpgCharacterData> find(int id) {

        RpgCharacterData rpgCharacterData = data.get(id);

        return Optional.ofNullable(rpgCharacterData);

    }

    @Override
    public void delete(int id) {
        data.remove(id);
    }

    @Override
    public void update(RpgCharacterData characterData) {
        if (characterData.id() == null) {
            throw new IllegalArgumentException("Cannot update character without id");
        }

        data.put(characterData.id(), characterData);
    }

    @Override
    public int create(RpgCharacterData characterData) {

        int id = nextId();

        RpgCharacterData characterWithId = characterData.copyUpdatingId(id);

        data.put(id, characterWithId);
        return id;
    }

    /// generate id from the last one
    private int nextId() {
        int max = data.keySet().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);
        return max + 1;
    }
}
