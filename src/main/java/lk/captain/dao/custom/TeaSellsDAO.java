package lk.captain.dao.custom;

import lk.captain.dao.CrudDAO;
import lk.captain.db.DbConnection;
import lk.captain.entity.TeaSells;
import lk.captain.view.tm.OrderCartTM;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TeaSellsDAO extends CrudDAO<TeaSells> {
   // public boolean saveOrder(String orderId, String cusId, String date, String teaTypeName, String time, ArrayList<OrderCartTM> tmLis) throws SQLException, ClassNotFoundException;

    boolean saveOrder(String orderId, String cusId, String date, String teaTypeName, String time, OrderCartTM tmLis) throws SQLException, ClassNotFoundException;
}
