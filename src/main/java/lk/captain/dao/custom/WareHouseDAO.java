package lk.captain.dao.custom;

import lk.captain.dao.CrudDAO;
import lk.captain.dao.SQLUtil;
import lk.captain.dao.SuperDAO;
import lk.captain.db.DbConnection;
import lk.captain.entity.WareHouse;
import lk.captain.view.tm.CartTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface WareHouseDAO extends CrudDAO<WareHouse>{

     double getCount() throws SQLException, ClassNotFoundException;
     boolean wareHouseUpdate(CartTM cartTM) throws SQLException, ClassNotFoundException;
     boolean isupdated(double reducedTeaTotal) throws SQLException, ClassNotFoundException;
}
