package school.coda.tp.jdbc.test;

import school.coda.tp.jdbc.rpg.RpgCharacterDao;
import school.coda.tp.jdbc.rpg.RpgCharacterData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class TestJdbc {
    static void main() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {

            RpgCharacterDao.createRpgCharacterTable(connection);

            List<RpgCharacterData> rpgCharacterData = RpgCharacterDao.getRpgCharacterData(connection);

            System.out.println(rpgCharacterData);

        } catch (SQLException e) {
            System.err.println(
                    "Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }

}

