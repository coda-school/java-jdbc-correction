package tp07.correction.infra.sql.sqlite;

import tp07.correction.infra.sql.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class GiftDatabaseSchema {
    private final ConnectionManager connectionManager;

    public GiftDatabaseSchema(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }


    public void initDdl() {
        Connection con = connectionManager.getConnection();
        try (Statement statement = con.createStatement()) {
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS gift_type (\
                    name varchar(20) primary key, \
                    logo varchar(2), \
                    label varchar(50) \
                    )""");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
