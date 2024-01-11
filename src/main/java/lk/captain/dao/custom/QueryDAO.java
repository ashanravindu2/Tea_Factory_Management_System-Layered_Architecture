package lk.captain.dao.custom;

import lk.captain.dao.SuperDAO;
import lk.captain.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public interface QueryDAO extends SuperDAO {

     ResultSet searchTransacCode(String id) throws SQLException, ClassNotFoundException;
     ResultSet searchTransacCodeWorker(String id) throws SQLException, ClassNotFoundException;
     ResultSet searchTransacCodeColec(String id) throws SQLException, ClassNotFoundException;
     HashMap getDetail(String time, String cusId) throws SQLException, ClassNotFoundException;
     String getTPrice(String time,String cusId) throws SQLException, ClassNotFoundException;
}
