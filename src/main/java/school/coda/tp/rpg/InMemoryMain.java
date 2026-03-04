package school.coda.tp.rpg;

import school.coda.tp.rpg.game.Jeu;
import school.coda.tp.rpg.persistance.RpgCharacters;
import school.coda.tp.rpg.persistance.inmemory.HashMapRpgCharacters;
import school.coda.tp.rpg.shared.TechnicalException;

/// Jeu avec persistance en mémoire
public class InMemoryMain {

    static void main() {

        try {

            // Configuration de la persistance en mémoire
            RpgCharacters rpgCharacters = new HashMapRpgCharacters();

            // Démarrer le jeu
            Jeu jeu = new Jeu(rpgCharacters, Jeu.Flag.GENERATE_SAMPLE_CHARACTERS);
            jeu.jouer();

        } catch (TechnicalException e) {
            System.err.println("Une erreur technique inattendue est survenue : " + e.getMessage());
        }


    }


}




