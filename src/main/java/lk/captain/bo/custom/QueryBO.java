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
    public ResultSet searchTransacCode(String id) throws SQLException, ClassNotFoundException;


    public ResultSet searchTransacCodeWorker(String id) throws SQLException, ClassNotFoundException;


    public ResultSet searchTransacCodeColec(String id) throws SQLException, ClassNotFoundException;

    public HashMap getDetail(String time, String cusId) throws SQLException, ClassNotFoundException;

    public  String getTPrice(String time,String cusId) throws SQLException, ClassNotFoundException;
}
