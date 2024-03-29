package lk.captain.dao.custom;

import lk.captain.dao.CrudDAO;
import lk.captain.db.DbConnection;
import lk.captain.entity.TeaPowder;
import lk.captain.view.tm.CartTM;

import java.sql.*;

public interface TeaPowderDAO extends CrudDAO<TeaPowder> {

     boolean saveOrder(CartTM cartTm) throws SQLException, ClassNotFoundException;
     boolean updateTeaPowder(CartTM cartTM) throws SQLException, ClassNotFoundException;
     double searchDate(String date) throws SQLException, ClassNotFoundException;
}
