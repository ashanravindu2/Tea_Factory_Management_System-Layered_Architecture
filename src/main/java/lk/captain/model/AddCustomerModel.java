package lk.captain.model;

import lk.captain.db.DbConnection;
import lk.captain.dto.AddCustomerDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddCustomerModel {
    public boolean addCustomer(AddCustomerDTO addCustomerDTO) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO customer (cusId,cusName,cusTele,cusAddress) VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,addCustomerDTO.getCusId());
        pstm.setString(2,addCustomerDTO.getCusName());
        pstm.setString(3,addCustomerDTO.getCusTele());
        pstm.setString(4,addCustomerDTO.getCusAddress());

        return pstm.executeUpdate() > 0;
    }

    public String generateCusId()throws SQLException{
        Connection connection =DbConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet=statement.executeQuery("SELECT cusId FROM customer ORDER BY cusId DESC LIMIT 1");
            boolean isExist = resultSet.next();

            if(isExist){
                String oldCusId = resultSet.getString(1);
                oldCusId =oldCusId.substring(1,oldCusId.length());
                int intId =Integer.parseInt(oldCusId);
                intId =intId+1;

                if (intId <10){
                    return "C00" +intId;
                } else if (intId <100) {
                    return "C0"+intId;

                }else {
                    return "C"+intId;
                }
            }else {
                return "C001";
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    public boolean updateCustomer(AddCustomerDTO dto) throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE customer SET cusName = ?,cusTele =? ,cusAddress=? WHERE cusId= ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getCusName());
        pstm.setString(2, dto.getCusTele());
        pstm.setString(3, dto.getCusAddress());
        pstm.setString(4, dto.getCusId());
        return pstm.executeUpdate() > 0;
    }

    public AddCustomerDTO searchCusId(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer WHERE cusId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        AddCustomerDTO dto = null;

        if(resultSet.next()) {

            String cusId = resultSet.getString(1);
            String cusName = resultSet.getString(2);
            String cusTele = resultSet.getString(3);
            String cusAddress = resultSet.getString(4);

            dto = new AddCustomerDTO(cusId, cusName, cusTele, cusAddress);
        }

        return dto;
    }
    public List<AddCustomerDTO> getAllCus() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<AddCustomerDTO> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String cusId = resultSet.getString(1);
            String cusName = resultSet.getString(2);
            String cusTele = resultSet.getString(3);
            String cusAddress = resultSet.getString(4);

            var dto = new AddCustomerDTO(cusId, cusName, cusTele, cusAddress);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
