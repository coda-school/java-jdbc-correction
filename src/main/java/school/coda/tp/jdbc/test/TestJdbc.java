package school.coda.tp.jdbc.test;

import school.coda.tp.jdbc.shared.TechnicalException;
import school.coda.tp.jdbc.sql.DatabaseMigration;
import school.coda.tp.jdbc.sql.RpgCharacterData;
import school.coda.tp.jdbc.sql.RpgCharacters;
import school.coda.tp.jdbc.sql.SqliteRpgCharacters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class TestJdbc {
    static void main() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {

            DatabaseMigration.createRpgCharacterTable(connection);
            RpgCharacters rpgCharacters = new SqliteRpgCharacters(connection);


            List<RpgCharacterData> rpgCharacterData = rpgCharacters.all();
            System.out.println(rpgCharacterData);


        } catch (SQLException e) {
            System.err.println(
                    "Erreur lors de la connexion à la base de données : " + e.getMessage());
        } catch (TechnicalException e) {
            System.err.println(
                    "Une erreur technique inattendue est survenue : " + e.getMessage());
        }
    }
}




