package school.coda.tp.rpg.persistance.sqlite;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/// Utilitaires pour créer la base de données SQlite
public class SqliteDatabaseMigration {

    public static void createRpgCharacterTable(Connection connection) {
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
