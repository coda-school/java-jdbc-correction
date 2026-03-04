package school.coda.tp.rpg;

import school.coda.tp.rpg.game.Jeu;
import school.coda.tp.rpg.persistance.RpgCharacters;
import school.coda.tp.rpg.persistance.sqlite.SqliteDatabaseMigration;
import school.coda.tp.rpg.persistance.sqlite.SqliteRpgCharacters;
import school.coda.tp.rpg.shared.TechnicalException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

/// Jeu avec persistance en base de données Sqlite
public class SqliteMain {
    static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {

            // Configuration de la persistance en base de données
            SqliteDatabaseMigration.createRpgCharacterTable(connection);
            RpgCharacters rpgCharacters = new SqliteRpgCharacters(connection);

            // Démarrer le jeu
            Jeu jeu = new Jeu(rpgCharacters);
            jeu.jouer();

        } catch (SQLException e) {
            System.err.println(
                    "Erreur lors de la connexion à la base de données : " + e.getMessage());
        } catch (TechnicalException e) {
            System.err.println(
                    "Une erreur technique inattendue est survenue : " + e.getMessage());
            if (Arrays.stream(args).anyMatch(s -> s.contains("debug"))) {
                System.err.println(
                        "DEBUG : " + e.getCause());
            }
        }
    }

}




