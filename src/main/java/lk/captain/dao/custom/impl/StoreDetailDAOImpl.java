package lk.captain.dao.custom.impl;

import com.lowagie.text.pdf.PRAcroForm;
import lk.captain.dao.SQLUtil;
import lk.captain.dao.custom.StoreDetailDAO;
import lk.captain.entity.StoreDetails;
import lk.captain.util.TransactionConnection;
import lk.captain.view.tm.OrderCartTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreDetailDAOImpl implements StoreDetailDAO {


    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
      return SQLUtil.execute("DELETE FROM storedetails WHERE teaTypeId = ?", id);
    }

    @Override
    public boolean save(StoreDetails dto) throws SQLException, ClassNotFoundException {
       Connection connection = TransactionConnection.setConnection();
        PreparedStatement stm = connection.prepareStatement("INSERT INTO storedetails VALUES(?, ?, ?, ?)");
        stm.setString(1, dto.getWareHouseId());
        stm.setString(2, dto.getTeaTypeId());
        stm.setString(3, dto.getDate());
        stm.setDouble(4, dto.getQuantity());
        if (stm.executeUpdate() != 1) {
            connection.rollback();
            TransactionConnection.setAutoCommitTrue();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(StoreDetails dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean updateTM(OrderCartTM orderCartTM) throws SQLException, ClassNotFoundException {
        Connection connection = TransactionConnection.setAutoCommitFalse();
       PreparedStatement stm = connection.prepareStatement("UPDATE storedetails SET quantity = quantity - ? WHERE teaTypeId = ?");
          stm.setDouble(1, orderCartTM.getQuantity());
            stm.setString(2, orderCartTM.getTeaTypeId());
            if (stm.executeUpdate() != 1) {
                connection.rollback();
                TransactionConnection.setAutoCommitTrue();
                return false;
            }
            return true;
    }

    @Override
    public StoreDetails search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM storedetails WHERE teaTypeId = ?", id);
        if (resultSet.next()) {
            return new StoreDetails(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4));
        }
        return null;
    }
    @Override
    public ResultSet generateId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<StoreDetails> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM storedetails");
        ArrayList<StoreDetails> dtoList = new ArrayList<>();
        while (resultSet.next()) {
            StoreDetails storeDetails = new StoreDetails(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            );
            dtoList.add(storeDetails);
        }
        return dtoList;
    }

    @Override
    public int searchCount() throws SQLException, ClassNotFoundException {
        return 0;
    }
}
