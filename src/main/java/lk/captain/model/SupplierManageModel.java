package lk.captain.model;


import lk.captain.Db.DbConnection;
import lk.captain.dto.SupplierManageDTO;
import lk.captain.dto.WorkerManageDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierManageModel {
    public static boolean supplierSave(SupplierManageDTO supplierManageDTO)throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO suppliers(supplierId,suppName,suppAddres,suppTele,suppGen) VALUES (?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,supplierManageDTO.getSupplierId());
        pstm.setString(2,supplierManageDTO.getSuppName());
        pstm.setString(3, supplierManageDTO.getSuppAddres());
        pstm.setString(4, supplierManageDTO.getSuppTele());
        pstm.setString(5, supplierManageDTO.getSuppGen());

        return pstm.executeUpdate()>0;
    }
    public String generateSupId()throws SQLException{
        Connection connection =DbConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet=statement.executeQuery("SELECT supplierId FROM suppliers ORDER BY supplierId DESC LIMIT 1");
            boolean isExist = resultSet.next();

            if(isExist){
                String oldSupId = resultSet.getString(1);
                oldSupId =oldSupId.substring(1,oldSupId.length());
                int intId =Integer.parseInt(oldSupId);
                intId =intId+1;

                if (intId <10){
                    return "S00" +intId;
                } else if (intId <100) {
                    return "S0"+intId;

                }else {
                    return "S"+intId;
                }
            }else {
                return "S001";
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<SupplierManageDTO> getAllTeaSupp() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM suppliers";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<SupplierManageDTO> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String supplierId = resultSet.getString(1);
            String suppName = resultSet.getString(2);
            String suppAddres = resultSet.getString(3);
            String suppTele = resultSet.getString(4);
            String suppGen =resultSet.getString(5);

            var dto = new SupplierManageDTO(supplierId, suppName, suppAddres, suppTele,suppGen);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public boolean deleteSupplier(String suppId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM suppliers WHERE supplierId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, suppId);

        return pstm.executeUpdate() > 0;
    }

    public boolean updateSupplier(SupplierManageDTO dto) throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE suppliers SET suppName = ?,suppAddres =? ,suppTele=?,suppGen=? WHERE supplierId= ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getSuppName());
        pstm.setString(2, dto.getSuppAddres());
        pstm.setString(3, dto.getSuppTele());
        pstm.setString(4, dto.getSuppGen());
        pstm.setString(5, dto.getSupplierId());


        return pstm.executeUpdate() > 0;
    }

    public SupplierManageDTO searchSupplierId(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM suppliers WHERE supplierId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        SupplierManageDTO dto = null;

        if(resultSet.next()) {

            String supplierId = resultSet.getString(1);
            String suppName = resultSet.getString(2);
            String suppAddress = resultSet.getString(3);
            String suppTele = resultSet.getString(4);
            String suppGen = resultSet.getString(5);

            dto = new SupplierManageDTO(supplierId, suppName, suppAddress, suppTele,suppGen);
        }

        return dto;
    }

}
