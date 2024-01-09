package lk.captain.model;

import lk.captain.db.DbConnection;
import lk.captain.dto.FuelMaterialDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuelManageModel {

    public String generateFuelId()throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet=statement.executeQuery("SELECT barrelId FROM matirials ORDER BY barrelId DESC LIMIT 1");
            boolean isExist = resultSet.next();

            if(isExist){
                String oldBId = resultSet.getString(1);
                oldBId =oldBId.substring(1,oldBId.length());
                int intId =Integer.parseInt(oldBId);
                intId =intId+1;

                if (intId <10){
                    return "B00" +intId;
                } else if (intId <100) {
                    return "B0"+intId;

                }else {
                    return "B"+intId;
                }
            }else {
                return "B001";
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }








    public List<FuelMaterialDTO> getAllFuels() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT matirials.barrelId, bCategory, bLeter\n" +
                "FROM matirials\n" +
                "WHERE  bCategory IS NOT NULL\n" +
                "  AND bLeter IS NOT NULL";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<FuelMaterialDTO> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String category = resultSet.getString(2);
            double liter = resultSet.getDouble(3);


            var dto = new FuelMaterialDTO(id, category, liter);
            dtoList.add(dto);
        }
        return dtoList;
    }
    public boolean usedUpdateFuel(String id ,double liter) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE matirials SET bLeter = bLeter-? WHERE barrelId= ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setDouble(1,liter );
        pstm.setString(2,id );


        return pstm.executeUpdate() > 0;
    }

        public ResultSet getAllAvalable() throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "select COUNT(bCategory)AS COUNTS,SUM(bLeter) AS LETER FROM matirials WHERE bCategory IS NOT NULL  AND bLeter IS NOT NULL";

            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();

            return resultSet;
        }
}
