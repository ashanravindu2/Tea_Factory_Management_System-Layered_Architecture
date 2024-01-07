package lk.captain.model;

import lk.captain.db.DbConnection;
import lk.captain.dto.StoreDetailsDTO;
import lk.captain.dto.tm.OrderCartTM;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StoreDetailsModel {

    WareHouseModel wareHouseModel = new WareHouseModel();

    public boolean store(String wareHouseId,String teaTypeId,String date,double quantity) throws SQLException {
        boolean result = false;
        Connection connection= null;

        try{
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
        String sql = "INSERT INTO storedetails VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, wareHouseId);
        pstm.setString(2, teaTypeId);
        pstm.setDate(3, Date.valueOf(date));
        pstm.setDouble(4,quantity);
        boolean isStored =  pstm.executeUpdate() > 0;

        if (isStored){
            boolean isReducedTeaPowder = wareHouseModel.updated(quantity);
            if (isReducedTeaPowder){
                connection.commit();
                result = true;
            }
        }
        }catch (SQLException e){
            connection.rollback();
        }finally {
            connection.setAutoCommit(true);
        }
        return result;
    }

    public List<StoreDetailsDTO> load() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM storedetails";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<StoreDetailsDTO> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            var dto = new StoreDetailsDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)

            );

            dtoList.add(dto);
        }

        return dtoList;
    }

    public boolean deletTeaStock(String teaTypeId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM storedetails WHERE teaTypeId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, teaTypeId);

        return pstm.executeUpdate() > 0;
    }

    public StoreDetailsDTO serchOnTeaType(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM storedetails WHERE teaTypeId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        StoreDetailsDTO dto = null;

        if(resultSet.next()) {
            String wareHouseId = resultSet.getString(1);
            String teaTypeId = resultSet.getString(2);
            String date = resultSet.getString(3);
            double quantity = resultSet.getDouble(4);

            dto = new StoreDetailsDTO(wareHouseId, teaTypeId, date, quantity);
        }

        return dto;

    }

    //sells
    public boolean updateStock(List<OrderCartTM> tmList) throws SQLException {
        for (OrderCartTM cartTm : tmList) {
            if(!updateStock(cartTm)) {
                return false;
            }
        }
        return true;
    }
    public boolean updateStock(OrderCartTM orderCartTM) throws SQLException{

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE storedetails SET quantity = quantity - ? WHERE teaTypeId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setDouble(1, orderCartTM.getQuantity());
        pstm.setString(2, orderCartTM.getTeaTypeId());

        return pstm.executeUpdate() > 0;//trues
    }




}