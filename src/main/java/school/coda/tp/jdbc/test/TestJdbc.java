package school.coda.tp.jdbc.test;

import school.coda.tp.jdbc.sql.CharacterJob;
import school.coda.tp.jdbc.sql.RpgCharacterData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestJdbc {
    static void main() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {

            createRpgCharacterTable(connection);

            List<RpgCharacterData> rpgCharacterData = getRpgCharacterData(connection);

            System.out.println(rpgCharacterData);

        } catch (SQLException e) {
            System.err.println(
                    "Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }

    private static List<RpgCharacterData> getRpgCharacterData(Connection connection) {
        List<RpgCharacterData> rpgCharacterData = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("""
                     SELECT id, name, hp, def, money, atk, heal, job
                     FROM rpg_character
                     """)) {
            while (resultSet.next()) {

                rpgCharacterData.add(new RpgCharacterData(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("hp"),
                        resultSet.getInt("def"),
                        resultSet.getInt("money"),
                        resultSet.getInt("atk"),
                        resultSet.getInt("heal"),
                        CharacterJob.fromName(resultSet.getString("job"))
                ));

            }
        } catch (SQLException e) {
            System.err.println("Erreur lors récupération de la liste de personnages : " + e.getMessage());
        }
        return rpgCharacterData;
    }

    private static void createRpgCharacterTable(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS rpg_character
                    (
                        id    integer      not null
                            constraint rpg_character_pk
                                primary key autoincrement,
                        name  varchar(255) not null,
                        hp    integer      not null,
                        def   integer      not null,
                        money integer      not null,
                        atk   integer,
                        heal  integer,
                        job   varchar(20)
                    );
                    """);
        } catch (SQLException e) {
            System.err.println("Erreur lors de création de la table rpg_character : " + e.getMessage());
        }
    }
}

