package lk.captain.dao;

import java.sql.SQLException;

public class AddCustomerDAOImpl implements AddCustomerDAO{

    @Override
    public boolean delete(String cusIds) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM customer WHERE cusId = ?", cusIds);
    }
}
