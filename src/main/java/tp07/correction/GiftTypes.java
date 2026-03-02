package tp07.correction;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

// Design Pattern Repository

/// Repository pour récupérer des informations sur les types de cadeaux
/// avec une interface similaire à une collection.
///
/// Ce design pattern permet de découpler la logique métier et la logique de persistance.
///
/// On peut par exemple écrire une implémentation
/// - en base de données
/// - via des objets en mémoire
/// - via des fichiers
///
/// Le fait d'utiliser une interface permet également de définir des "Test Double" pour substituer les comportements
/// attendus du repository dans des tests automatisés sans avoir à disposer d'une vraie base de données.
///
/// On peut considérer que le Design Pattern Repository est une sorte de spécialisation du pattern Facade pour
/// simplifier une interaction avec une technologie de persistance.
public interface GiftTypes {

    Set<GiftType> findAll();

    Optional<GiftType> findByName(String name);

    Set<String> allNames();

    void add(GiftType giftType);

    void deleteByName(String name);

    void update(GiftType giftType);

    Collection<GiftType> seedInitialData();
}
