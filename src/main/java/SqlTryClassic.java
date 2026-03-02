import tp07.correction.shared.TechnicalException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

void main() {
    // Avec user et mot de passe
    Connection connection = null;
    try {
        connection = DriverManager.getConnection("jdbc:sqlite:gifts.db");
        Set<String> names = allNames(connection);
        System.out.println(names);

    } catch (SQLException e) {
        // Handle exception silently in finally block
        throw new TechnicalException(TechnicalException.ErrorCode.DATABASE_ERROR,e);
    } finally {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            // Handle exception silently in finally block
        }
    }

}

private static Set<String> allNames(Connection connection) {
    Statement statement = null;
    ResultSet resultSet = null;
    try {
        statement = connection.createStatement();
        resultSet = statement.executeQuery(" SELECT name " +
                                           " FROM gift_type ");
        Set<String> allGiftTypeNames = new HashSet<>();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            allGiftTypeNames.add(name);
        }
        return allGiftTypeNames;
    } catch (SQLException e) {
        throw new TechnicalException( TechnicalException.ErrorCode.DATABASE_ERROR,e);
    } finally {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                // Handle exception silently in finally block
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                // Handle exception silently in finally block
            }
        }
    }
}