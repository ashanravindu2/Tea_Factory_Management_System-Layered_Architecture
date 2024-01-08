package lk.captain.model;

import lk.captain.db.DbConnection;
import lk.captain.dto.FuelMaterialDTO;
import lk.captain.dto.WoodMaterialDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WoodManageModel {


    public boolean usedUpdateWood(String id, double used) throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "UPDATE matirials SET wWeight = wWeight-? WHERE barrelId= ?";
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setDouble(1,used );
            pstm.setString(2,id );


            return pstm.executeUpdate() > 0;
        }




    public ResultSet getAllAvalable() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select COUNT(wCategory)AS COUNTS,SUM(wWeight) AS WEIGHT FROM matirials WHERE wCategory IS NOT NULL  AND wWeight IS NOT NULL ";

        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        return resultSet;
    }
}
