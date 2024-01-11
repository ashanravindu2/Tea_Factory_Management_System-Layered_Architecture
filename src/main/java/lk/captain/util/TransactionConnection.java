package lk.captain.util;


import lk.captain.db.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionConnection {
    private static Connection connection;

    public static Connection setConnection() throws SQLException, ClassNotFoundException {
        connection = DbConnection.getDbConnection().getConnection();
        return connection;
    }

    public static Connection setAutoCommitFalse() throws SQLException {
        connection.setAutoCommit(false);
        return connection;
    }

    public static Connection setAutoCommitTrue() throws SQLException {
        connection.setAutoCommit(true);
        return connection;
    }

    public static Connection getConnection() {
        return connection;
    }
}
