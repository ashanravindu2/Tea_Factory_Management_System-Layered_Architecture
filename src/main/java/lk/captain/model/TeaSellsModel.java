package lk.captain.model;

import lk.captain.db.DbConnection;
import lk.captain.dto.TeaSellsDTO;
import lk.captain.dto.tm.OrderCartTM;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class TeaSellsModel {

    StoreDetailsModel storeDetailsModel = new StoreDetailsModel();


    public String generateNextOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
    try{
        String sql = "SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        boolean isExist = resultSet.next();

        if(isExist){
            String oldOrderId = resultSet.getString(1);
            oldOrderId =oldOrderId.substring(1,oldOrderId.length());
            int intId =Integer.parseInt(oldOrderId);
            intId =intId+1;

            if (intId <10){
                return "O00" +intId;
            } else if (intId <100) {
                return "O0"+intId;

            }else {
                return "O"+intId;
            }
        }else {
            return "O001";
        }
    }catch (SQLException e){
        e.printStackTrace();
    }
        return null;

    }

    public boolean placeOrder(TeaSellsDTO pDto) throws SQLException {
        boolean result = false;
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSaved = saveOrder(pDto.getOrderId(), pDto.getCusId(), pDto.getDate(), pDto.getTeaTypeName(), pDto.getTime(), pDto.getTmList());
            if (isOrderSaved) {
                boolean isTeaTypesStockUpdated = storeDetailsModel.updateStock(pDto.getTmList());
                if (isTeaTypesStockUpdated) {
                    connection.commit();
                    result = true;
                }
            }

        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return result;
    }

    public boolean saveOrder(String orderId, String cusId, String date, String teaTypeName, String time, List<OrderCartTM> tmList) throws SQLException {
        for (OrderCartTM cartTm : tmList) {
            if (!saveOrder(orderId, cusId, date, teaTypeName, time, cartTm)) {
                return false;
            }
        }
        return true;
    }

    public boolean saveOrder(String orderId, String cusId, String date, String teaTypeName, String time, OrderCartTM orderCartTM) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO orders VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, orderId);
        pstm.setString(2, cusId);
        pstm.setDate(3, Date.valueOf(date));
        pstm.setDouble(4, orderCartTM.getUnitPrice());
        pstm.setString(5, orderCartTM.getTeaTypeId());
        pstm.setDouble(6, orderCartTM.getQuantity());
        pstm.setDouble(7, orderCartTM.getTotal());
        pstm.setString(8, time);
        return pstm.executeUpdate() > 0;
    }

    public HashMap getDetail(String time,String cusId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT orders.unitPrice, orders.quantity, orders.total, tea_types.teaTypeName\n" +
                "FROM orders\n" +
                "         JOIN tea_types ON orders.teaTypeId = tea_types.teaTypeId\n" +
                "WHERE orders.cusId = ? AND orders.time = ? ";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,cusId);
        pstm.setString(2,time);
        ResultSet resultSet = pstm.executeQuery();

        HashMap myMap1 = new HashMap<>();
        while (resultSet.next()){
            String unitPrice = String.valueOf(resultSet.getDouble("unitPrice"));
            String quantity = String.valueOf(resultSet.getDouble("quantity"));
            String total = String.valueOf(resultSet.getDouble("total"));
            String teaTypeName = resultSet.getString("teaTypeName");

            HashMap myMap = new HashMap<>();
            myMap.put(1,unitPrice);
            myMap.put(2,quantity);
            myMap.put(3,total);
            myMap.put(4,teaTypeName);

            myMap1=myMap;
        }
    return myMap1;
    }

    public static String getTPrice(String time,String cusId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT SUM(total) AS NetTotal FROM orders JOIN tea_types ON orders.teaTypeId = tea_types.teaTypeId WHERE orders.cusId = ? AND orders.time = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, cusId);
        pstm.setString(2, time);
        ResultSet resultSet = pstm.executeQuery();

        String netTotal = null;
        if(resultSet.next()){
            String netTot = resultSet.getString("NetTotal");
            netTotal=netTot;
        }

      return netTotal;

    }


}