package lk.captain.bo.custom;

import lk.captain.bo.SuperBO;
import lk.captain.dao.SQLUtil;
import lk.captain.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public interface QueryBO extends SuperBO {

     ResultSet searchTransacCode(String id) throws SQLException, ClassNotFoundException;
     ResultSet searchTransacCodeWorker(String id) throws SQLException, ClassNotFoundException;
     ResultSet searchTransacCodeColec(String id) throws SQLException, ClassNotFoundException;
     HashMap getDetail(String time, String cusId) throws SQLException, ClassNotFoundException;
     String getTPrice(String time,String cusId) throws SQLException, ClassNotFoundException;
}
