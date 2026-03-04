package school.coda.tp.rpg.game;

import school.coda.rpg.character.job.RpgCharacter;
import school.coda.tp.rpg.persistance.RpgCharacters;
import school.coda.tp.rpg.persistance.model.CharacterJob;
import school.coda.tp.rpg.persistance.model.RpgCharacterData;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/// Le jeu peut être lancé sans se préoccuper
/// de comment sont stockées les données
///
/// En utilisant le polymorphisme
/// via héritage de type de l'interface {@link RpgCharacters}
public class Jeu {

    /// Utilitaire de persistance pour charger et sauvegarder les
    /// données des personnages.
    ///
    /// @see <a href="https://fr.wikipedia.org/wiki/Injection_de_d%C3%A9pendances">Design Pattern : injection de dépendance</a>
    private final RpgCharacters rpgCharacters;

    public Jeu(RpgCharacters rpgCharacters, Flag... flags) {
        this.rpgCharacters = rpgCharacters;

        // Activation des options
        if (Arrays.asList(flags).contains(Flag.GENERATE_SAMPLE_CHARACTERS)) {
            generateSampleCharacters();
        }
    }

    /// Une démo pour montrer le fonctionnement de la couche de persistance
    public void demoExo2() {

        // Affiche la liste des personnages en persistance
        List<RpgCharacterData> all = rpgCharacters.all();
        System.out.println(all);

        // Récupère le premier personnage
        RpgCharacterData first = all.getFirst();
        Integer id = first.id();

        // Le stockage peut ne pas contenir de données
        Optional<RpgCharacterData> optionalFirst = rpgCharacters.find(id);

        if (optionalFirst.isPresent()) {
            RpgCharacterData firstCharacter = optionalFirst.get();
            System.out.println("First character is : " + firstCharacter);

            String newName = "Toto";

            RpgCharacterData characterToUpdate = firstCharacter.copyUpdatingName(newName);

            // Changement du nom du premier personnage
            rpgCharacters.update(characterToUpdate);
            System.out.println("Updated first character name to : " + newName);
        } else {
            System.err.println("J'ai pas trouvé le personnage d'id : " + id);
        }


        List<RpgCharacterData> allPaladins = rpgCharacters.allByJob(CharacterJob.PALADIN);
        System.out.println();
        System.out.println("All paladins : " + allPaladins);
        System.out.println();

        List<RpgCharacterData> allMages = rpgCharacters.allByJob(CharacterJob.MAGE);
        System.out.println();
        System.out.println("All mages : " + allMages);
        System.out.println();

        List<RpgCharacterData> allWarriors = rpgCharacters.allByJob(CharacterJob.WARRIOR);
        System.out.println();
        System.out.println("All warriors : " + allWarriors);
        System.out.println();


        // Supprimer un personnage
        rpgCharacters.create(new RpgCharacterData("Deuxfleurs le touriste", 10, 2, 5000, null, null, null));
    }

    /// Utilisation de la persistance pour charger des vrais personnages de jeu
    public void startGameExo3() {
        // TODO : exo 3
    }

    /// Création de personnages pré-tirés
    private void generateSampleCharacters() {
        rpgCharacters.create(new RpgCharacterData(
                "Rincevent", 50, 2,
                20, CharacterJob.MAGE, 2, null
        ));
        rpgCharacters.create(new RpgCharacterData(
                "Ridculle", 70, 2,
                200, CharacterJob.MAGE, 5, null
        ));
        rpgCharacters.create(new RpgCharacterData(
                "Cohen le barbare", 150, 7,
                120, CharacterJob.WARRIOR, 10, null
        ));
        rpgCharacters.create(new RpgCharacterData(
                "Lothar", 120, 5,
                80, CharacterJob.PALADIN, 7, 10
        ));
    }

    ///  Permet d'activer des comportements optionnels au démarrage du jeu
    public enum Flag {
        /// Lors du démarrage du jeu, génère des
        GENERATE_SAMPLE_CHARACTERS
    }
}
