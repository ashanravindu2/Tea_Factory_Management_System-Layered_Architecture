package lk.captain.dao.custom;

import lk.captain.dao.SQLUtil;
import lk.captain.entity.AddCustomer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddCustomerDAOImpl implements AddCustomerDAO{

    @Override
    public boolean delete(String cusIds) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM customer WHERE cusId = ?", cusIds);
    }

    @Override
    public boolean save(AddCustomer dto) throws SQLException, ClassNotFoundException {
       return SQLUtil.execute("INSERT INTO customer (cusId,cusName,cusTele,cusAddress) VALUES(?, ?, ?, ?)",dto.getCusId(),dto.getCusName(),dto.getCusTele(),dto.getCusAddress());
    }

    @Override
    public boolean update(AddCustomer dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE customer SET cusName=?,cusTele=?,cusAddress=? WHERE cusId=?",dto.getCusName(),dto.getCusTele(),dto.getCusAddress(),dto.getCusId());
    }

    @Override
    public AddCustomer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer WHERE cusId=?", id);

        if (rst.next()) {
            return new AddCustomer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
        }
        return null;
    }

    @Override
    public ResultSet generateId() throws SQLException, ClassNotFoundException {
            return SQLUtil.execute("SELECT cusId FROM customer ORDER BY cusId DESC LIMIT 1");

    }


    @Override
    public ArrayList<AddCustomer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet =SQLUtil.execute("SELECT * FROM customer");
        ArrayList<AddCustomer> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            AddCustomer addCustomer = new AddCustomer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
         dtoList.add(addCustomer);
        }
        return dtoList;
    }

    @Override
    public int searchCount() throws SQLException, ClassNotFoundException {
        return 0;
    }


}
