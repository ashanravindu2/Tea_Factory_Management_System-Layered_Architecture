package lk.captain.dao.custom.impl;

import lk.captain.dao.SQLUtil;
import lk.captain.dao.custom.TeaPowderDAO;
import lk.captain.db.DbConnection;
import lk.captain.dto.TeaPowderGetDTO;
import lk.captain.entity.TeaPowder;
import lk.captain.util.TransactionConnection;
import lk.captain.view.tm.CartTM;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeaPowderDAOImpl implements TeaPowderDAO {
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
            return SQLUtil.execute( "DELETE FROM teepowder WHERE date = ?",id);
    }

    @Override
    public boolean save(TeaPowder dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(TeaPowder dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public TeaPowder search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ResultSet generateId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<TeaPowder> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM teepowder");
        ArrayList<TeaPowder> teaPowderList = new ArrayList<>();
        while (resultSet.next()) {
           teaPowderList.add(new TeaPowder(
                    resultSet.getString(1),
                    resultSet.getDouble(2)));
        }
        return teaPowderList;
    }

    @Override
    public int searchCount() throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public boolean saveOrder(CartTM cartTm) throws SQLException, ClassNotFoundException {
        Connection connection = TransactionConnection.setConnection();

       PreparedStatement stm = connection.prepareStatement("INSERT INTO teepowder VALUES(?, ?)");
        stm.setString(1, cartTm.getDate());
        stm.setDouble(2, cartTm.getUseTea());
        if (stm.executeUpdate() != 1) {
            connection.rollback();
            TransactionConnection.setAutoCommitTrue();
            return false;
        }
        return true;

    }

    @Override
    public boolean updateTeaPowder(CartTM cartTM) throws SQLException, ClassNotFoundException {
        Connection connection = TransactionConnection.setConnection();
        PreparedStatement stm = connection.prepareStatement( "UPDATE teepowder SET useTea=useTea+ ? WHERE date = ?");
        stm.setDouble(1, cartTM.getUseTea());
        stm.setString(2, cartTM.getDate());
        if (stm.executeUpdate() != 1) {
            connection.rollback();
            TransactionConnection.setAutoCommitTrue();
            return false;
        }
        return true;
    }

    @Override
    public double searchDate(String date) throws SQLException, ClassNotFoundException {
      ResultSet resultSet= SQLUtil.execute("SELECT useTea FROM teepowder WHERE date= ?",date);
            if (resultSet.next()) {
                double useTea = resultSet.getDouble("useTea");
                return useTea;
            }
            return 0;
        }
}
