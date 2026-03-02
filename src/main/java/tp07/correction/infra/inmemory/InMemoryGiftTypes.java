package tp07.correction.infra.inmemory;

import tp07.correction.shared.CouldNotAddGiftTypeException;
import tp07.correction.GiftType;
import tp07.correction.shared.CouldNotUpdateGiftTypeException;
import tp07.correction.GiftTypes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class InMemoryGiftTypes implements GiftTypes {
    private final Map<String, GiftType> giftTypes;

    public InMemoryGiftTypes() {
        this.giftTypes = new HashMap<>();
    }

    @Override
    public Set<GiftType> findAll() {
        return giftTypes.values().stream().collect(Collectors.toSet());
    }

    @Override
    public Optional<GiftType> findByName(String name) {
        return Optional.ofNullable(giftTypes.get(name));
    }

    @Override
    public Set<String> allNames() {
        return giftTypes.keySet();
    }

    @Override
    public void add(GiftType giftType) {
        if (giftTypes.containsKey(giftType.name()))
            throw new CouldNotAddGiftTypeException("A gift type already exists with name : " + giftType.name());
        giftTypes.put(giftType.name(), giftType);
    }

    @Override
    public void deleteByName(String name) {
        giftTypes.remove(name);
    }

    @Override
    public void update(GiftType giftType) {
        if (giftTypes.containsKey(giftType.name())) {
            giftTypes.put(giftType.name(), giftType);
        } else {
            throw new CouldNotUpdateGiftTypeException("No gift type exists with name : " + giftType.name());
        }
    }

    @Override
    public Collection<GiftType> seedInitialData() {
        Set<GiftType> initialData = Set.of(
                new GiftType("robot", "🤖", "Robot articulé"),
                new GiftType("teddy", "🧸", "Ourson en peluche"),
                new GiftType("car", "🚗", "Petite voiture"),
                new GiftType("doll", "🪆", "Poupée magique"),
                new GiftType("book", "📚", "Livre enchanté"));
        initialData.forEach(giftType -> giftTypes.put(giftType.name(), giftType));
        return initialData;
    }
}
