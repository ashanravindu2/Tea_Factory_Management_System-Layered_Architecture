package lk.captain.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.captain.Db.DbConnection;
import lk.captain.dto.FuelMaterialDTO;
import lk.captain.dto.SupplierManageDTO;
import lk.captain.dto.TeaTypeDTO;
import lk.captain.dto.tm.FuelMatiralTM;
import lk.captain.dto.tm.TeaSupplierManageTM;

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

    public boolean fuelSave(FuelMaterialDTO fuelMaterialDTO) throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "INSERT INTO matirials(barrelId,bCategory,bLeter) VALUES (?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,fuelMaterialDTO.getBarrelId());
            pstm.setString(2,fuelMaterialDTO.getBCategory());
            pstm.setDouble(3,fuelMaterialDTO.getBLeter());

            return pstm.executeUpdate()>0;
        }

    public List<FuelMaterialDTO> getAllFuel() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT matirials.barrelId, bCategory, bLeter\n" +
                "FROM matirials\n" +
                "WHERE  bCategory IS NOT NULL\n" +
                "  AND bLeter IS NOT NULL;";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<FuelMaterialDTO> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String barrelId = resultSet.getString(1);
            String bCategory = resultSet.getString(2);
            double bLeter = resultSet.getDouble(3);


            var dto = new FuelMaterialDTO(barrelId, bCategory, bLeter);
            dtoList.add(dto);
        }
        return dtoList;
    }
    public boolean deleteFuel(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE matirials SET bCategory = NULL, bLeter = NULL WHERE barrelId = ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public FuelMaterialDTO searchFuelId(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "select matirials.barrelId,bCategory,bLeter FROM matirials where barrelId= ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        FuelMaterialDTO dto = null;

        if(resultSet.next()) {
            String code = resultSet.getString(1);
            String bCategory = resultSet.getString(2);
            double bLeter = resultSet.getDouble(3);

            dto = new FuelMaterialDTO(code,bCategory, bLeter);
        }

        return dto;
    }

    public boolean updateFuel(FuelMaterialDTO dto) throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "UPDATE matirials SET bCategory = ?, bLeter = ? WHERE barrelId= ?";
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, dto.getBCategory());
            pstm.setDouble(2, dto.getBLeter());
            pstm.setString(3, dto.getBarrelId());

            return pstm.executeUpdate() > 0;
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
