package school.coda.tp.rpg.persistance;

import school.coda.tp.rpg.persistance.model.CharacterJob;
import school.coda.tp.rpg.persistance.model.RpgCharacterData;
import school.coda.tp.rpg.shared.TechnicalException;

import java.util.List;
import java.util.Optional;

/**
 * Permet de lire, écrire, modifier, supprimer depuis une source de données générique.
 * <p>
 * Peut être implémenté par une base de données, en mémoire, depuis des fichiers...
 * <p>
 * Aucun détails d'implémetation ne devrait être visible dans cette interface
 */
public interface RpgCharacters {

    /**
     * Récupère tous les personnages
     * Peut lever {@link TechnicalException}
     */
    List<RpgCharacterData> all();

    /**
     * Récupère tous les personnages pour un métier donné
     * @param job métier
     * Peut lever {@link TechnicalException}
     * @return tous les personnages
     */
    List<RpgCharacterData> allByJob(CharacterJob job);

    /**
     * Récupère un personnage par son identifiant
     * @param id
     * Peut lever {@link TechnicalException}
     * @return le personnage ou vide
     */
    Optional<RpgCharacterData> find(int id);

    /**
     * Supprime un personnage par son identifiant
     * @param id
     * Peut lever {@link TechnicalException}
     */
    void delete(int id);

    /**
     * Supprime un personnage par son identifiant
     * @param characterData
     * Peut lever {@link TechnicalException}
     */
    void update(RpgCharacterData characterData);

    /**
     * Créer un personnage
     * <p>
     * <b>Note:</b> l'id du paramètre sera ignoré car il est généré par la couche de persistance.
     * @param characterData
     * Peut lever {@link TechnicalException}
     * @return identifiant du personnage créé
     */
    int create(RpgCharacterData characterData);


}
