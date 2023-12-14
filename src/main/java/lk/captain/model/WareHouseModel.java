package lk.captain.model;

import lk.captain.Db.DbConnection;

import java.sql.*;

public class WareHouseModel {

    public double getTeapowderTotl() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT SUM(stockCount) AS total FROM warehouse";

        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        double netTotal = 0;
        if (resultSet.next()) {
            netTotal = resultSet.getDouble("total");
        }
        return netTotal;
    }
    public static boolean updated(double reducedTeaTotal) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();
        String warehousseId = "W001";

        String sql = "UPDATE warehouse SET stockCount=stockCount- ? WHERE wareHouseId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setDouble(1,reducedTeaTotal );
        pstm.setString(2, warehousseId);
        return pstm.executeUpdate() > 0;

    }

}
