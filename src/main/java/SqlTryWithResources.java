import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

void main() {
    // Avec user et mot de passe
    try (Connection connection = DriverManager.getConnection("jdbc:sqlite:gifts.db")) {

        Set<String> names = allNames(connection);

        System.out.println(names);
    } catch (SQLException e) {
        // Handle exception
        e.printStackTrace();
    }


//    // Sans user et mot de passe
//    Connection connection = null;
//    try {
//        connection = DriverManager.getConnection("jdbc:sqlite:gifts.db");
//    } catch (SQLException e) {
//        throw new RuntimeException(e);
//    } finally {
//        try {
//            if (connection != null) connection.close();
//        } catch (SQLException e) {
//            // Handle exception
//            e.printStackTrace();
//        }
//    }
}

private static Set<String> allNames(Connection connection) throws SQLException {
    // Les ressources Statement et ResultSet sont automatiquement fermées
    // grâce au bloc try with resources
    try (Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(" SELECT name " +
                                                      " FROM gift_type ");
    ) {
        Set<String> allGiftTypeNames = new HashSet<>();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            allGiftTypeNames.add(name);
        }
        return allGiftTypeNames;
    }
}