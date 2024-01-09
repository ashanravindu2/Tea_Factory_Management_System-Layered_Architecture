package lk.captain.dao.custom;

import lk.captain.dao.CrudDAO;
import lk.captain.dao.SQLUtil;
import lk.captain.db.DbConnection;
import lk.captain.entity.WareHouse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WareHouseDAOImpl implements WareHouseDAO {
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean save(WareHouse dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(WareHouse dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public WareHouse search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ResultSet generateId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<WareHouse> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public int searchCount() throws SQLException, ClassNotFoundException {
        return 0;
    }

    public double getCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute( "SELECT SUM(stockCount) AS total FROM warehouse");
        double netTotal = 0;
        if (resultSet.next()) {
            netTotal = resultSet.getDouble("total");
        }
        return netTotal;
    }
    public boolean isupdated(double reducedTeaTotal) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE warehouse SET stockCount=stockCount- ? WHERE wareHouseId = ?",reducedTeaTotal,"W001");
    }
}
