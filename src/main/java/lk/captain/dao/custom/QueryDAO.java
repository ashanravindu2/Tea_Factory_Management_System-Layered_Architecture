package lk.captain.dao.custom;

import lk.captain.dao.SuperDAO;
import lk.captain.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public interface QueryDAO extends SuperDAO {
    public ResultSet searchTransacCode(String id) throws SQLException, ClassNotFoundException;

    public ResultSet searchTransacCodeWorker(String id) throws SQLException, ClassNotFoundException;

    public ResultSet searchTransacCodeColec(String id) throws SQLException, ClassNotFoundException;

    public HashMap getDetail(String time, String cusId) throws SQLException, ClassNotFoundException;

    public  String getTPrice(String time,String cusId) throws SQLException, ClassNotFoundException;
}
