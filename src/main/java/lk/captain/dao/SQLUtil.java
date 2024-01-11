package lk.captain.dao;

import lk.captain.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUtil {

        public static <T> T execute(String sql, Object... arg) throws SQLException, ClassNotFoundException {
            Connection connection = DbConnection.getDbConnection().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            for (int i = 0; i < arg.length; i++) {
                pstm.setObject((i+1), arg[i]);
            }

            if (sql.startsWith("SELECT") || sql.startsWith("select")) {
                return (T)pstm.executeQuery();
            }
            else {
                return (T)(Boolean)(pstm.executeUpdate()>0);
            }
        }
}
