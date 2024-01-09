package lk.captain.dao.custom;

import lk.captain.dao.CrudDAO;
import lk.captain.dao.SQLUtil;
import lk.captain.dao.SuperDAO;
import lk.captain.entity.WareHouse;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface WareHouseDAO extends CrudDAO<WareHouse>{
     double getCount() throws SQLException, ClassNotFoundException;

     boolean isupdated(double reducedTeaTotal) throws SQLException, ClassNotFoundException;
}
