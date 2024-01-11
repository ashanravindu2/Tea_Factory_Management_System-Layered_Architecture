package lk.captain.bo.custom;

import lk.captain.bo.SuperBO;
import lk.captain.dao.SQLUtil;
import lk.captain.db.DbConnection;
import lk.captain.view.tm.CartTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface WareHouseBO extends SuperBO {

     double getCount() throws SQLException, ClassNotFoundException;
     boolean isupdated(double reducedTeaTotal) throws SQLException, ClassNotFoundException;
     boolean wareHouseUpdate(CartTM cartTM) throws SQLException, ClassNotFoundException;
}
