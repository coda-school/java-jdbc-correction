package tp07.correction.infra.sql;

import tp07.correction.shared.TechnicalException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static tp07.correction.shared.TechnicalException.ErrorCode.DATABASE_ERROR;
import static tp07.correction.shared.TechnicalException.ErrorCode.UNABLE_TO_CLOSE_DATABASE_RESOURCE;

/// Connection wrapper to reuse is at different places of the program
public class ConnectionManager implements AutoCloseable {

    private final Connection con;

    public ConnectionManager(String url, String username, String password) throws TechnicalException {
        try {
            this.con = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new TechnicalException(DATABASE_ERROR, e);
        }
    }

    public ConnectionManager(String url) {
        this(url, null, null);
    }

    public Connection getConnection() {
        return con;
    }


    @Override
    public void close() {

        if (this.con != null) {
            if (this.isClosed()) {
                return;
            }
            try {
                this.con.close();
            } catch (SQLException e) {
                throw new TechnicalException(UNABLE_TO_CLOSE_DATABASE_RESOURCE, e);
            }
        }
    }

    private boolean isClosed() {
        try {
            return this.con.isClosed();
        } catch (SQLException e) {
            throw new TechnicalException(UNABLE_TO_CLOSE_DATABASE_RESOURCE, e);
        }
    }
}
