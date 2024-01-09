package lk.captain.bo.custom;

import lk.captain.bo.SuperBO;
import lk.captain.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface WareHouseBO extends SuperBO {
    public double getCount() throws SQLException, ClassNotFoundException;

    public boolean isupdated(double reducedTeaTotal) throws SQLException, ClassNotFoundException;
}
