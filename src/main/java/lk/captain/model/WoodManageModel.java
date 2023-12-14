package lk.captain.model;

import lk.captain.Db.DbConnection;
import lk.captain.dto.FuelMaterialDTO;
import lk.captain.dto.WoodMaterialDTO;
import lk.captain.dto.WorkerManageDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WoodManageModel {
    public boolean woodSave(FuelMaterialDTO fuelMaterialDTO) throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "INSERT INTO matirials(barrelId,wCategory,wWeight) VALUES (?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,fuelMaterialDTO.getBarrelId());
            pstm.setString(2,fuelMaterialDTO.getBCategory());
            pstm.setDouble(3,fuelMaterialDTO.getBLeter());

            return pstm.executeUpdate()>0;
        }

    public List<WoodMaterialDTO> getAllWoodId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT matirials.barrelId, wCategory, wWeight\n" +
                "FROM matirials\n" +
                "WHERE  wCategory IS NOT NULL\n" +
                "  AND wWeight IS NOT NULL";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<WoodMaterialDTO> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String wCategory = resultSet.getString(2);
            double wWeight = resultSet.getDouble(3);


            var dto = new WoodMaterialDTO(id, wCategory, wWeight);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public boolean usedUpdateWood(String id, double used) throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "UPDATE matirials SET wWeight = wWeight-? WHERE barrelId= ?";
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setDouble(1,used );
            pstm.setString(2,id );


            return pstm.executeUpdate() > 0;
        }

        public WoodMaterialDTO searchWoodId(String id) throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "select matirials.barrelId,wCategory,wWeight FROM matirials where barrelId= ? ";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, id);

            ResultSet resultSet = pstm.executeQuery();

            WoodMaterialDTO dto = null;

            if(resultSet.next()) {
                String code = resultSet.getString(1);
                String Category = resultSet.getString(2);
                double Weight = resultSet.getDouble(3);

                dto = new WoodMaterialDTO(code,Category,Weight);
            }

            return dto;
        }
    public boolean deletedWIds(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE matirials SET wCategory = NULL, wWeight = NULL WHERE barrelId = ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public boolean updateWood(WoodMaterialDTO dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE matirials SET wCategory = ?, wWeight = ? WHERE barrelId= ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getWCategory());
        pstm.setDouble(2, dto.getWWeight());
        pstm.setString(3, dto.getBarrelId());

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
