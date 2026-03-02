package tp07.step1;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    static void main() {
        try {
            DriverManager.getConnection("jdbc:sqlite:test.db");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données : "+e.getMessage());
        }

    }
}
