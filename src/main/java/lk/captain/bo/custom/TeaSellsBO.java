package lk.captain.bo.custom;

import lk.captain.bo.SuperBO;
import lk.captain.db.DbConnection;
import lk.captain.dto.TeaSellsDTO;
import lk.captain.view.tm.OrderCartTM;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface TeaSellsBO extends SuperBO {
    public ResultSet generateNextOrderId() throws SQLException, ClassNotFoundException;
    public boolean placeOrder(TeaSellsDTO pDto) throws SQLException, ClassNotFoundException;
    public boolean saveOrderTm(String orderId, String cusId, String date, String teaTypeName, String time, ArrayList<OrderCartTM> tmLis) throws SQLException, ClassNotFoundException;
    public boolean saveOrder(String orderId, String cusId, String date, String teaTypeName, String time, OrderCartTM orderCartTM) throws SQLException, ClassNotFoundException;
}
