package lk.captain.dao.custom.impl;

import lk.captain.dao.SQLUtil;
import lk.captain.dao.custom.TeaSellsDAO;
import lk.captain.entity.TeaSells;
import lk.captain.util.TransactionConnection;
import lk.captain.view.tm.OrderCartTM;

import java.sql.*;
import java.util.ArrayList;

public class TeaSellsDAOImpl implements TeaSellsDAO {
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean save(TeaSells dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(TeaSells dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public TeaSells search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ResultSet generateId() throws SQLException, ClassNotFoundException {
      return SQLUtil.execute("SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1");
    }

    @Override
    public ArrayList<TeaSells> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public int searchCount() throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public boolean saveOrder(String orderId, String cusId, String date, String teaTypeName, String time, OrderCartTM tmLis) throws SQLException, ClassNotFoundException {
        Connection connection = TransactionConnection.setConnection();
        PreparedStatement stm = connection.prepareStatement("INSERT INTO orders VALUES(?, ?, ?, ?, ?, ?, ?, ?)");

        for (OrderCartTM orderCartTM : tmLis) {
            stm.setString(1, orderId);
            stm.setString(2, cusId);
            stm.setString(3, date);
            stm.setDouble(4, orderCartTM.getUnitPrice());
            stm.setString(5, orderCartTM.getTeaTypeId());
            stm.setDouble(6, orderCartTM.getQuantity());
            stm.setDouble(7, orderCartTM.getTotal());
            stm.setString(8, time);
            if (stm.executeUpdate() != 1) {
                connection.rollback();
                TransactionConnection.setAutoCommitTrue();
                return false;
            }
        }
        return true;
    }
}
