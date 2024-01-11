package lk.captain.dao.custom.impl;

import lk.captain.dao.SQLUtil;
import lk.captain.dao.custom.QueryDAO;
import lk.captain.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public ResultSet searchTransacCode(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("SELECT payment.*, suppliers.suppName\n" +
                "FROM payment\n" +
                "         JOIN suppliers ON payment.EmpId = suppliers.supplierId\n" +
                "WHERE payment.transctionId = ?;\n",id);
    }

    @Override
    public ResultSet searchTransacCodeWorker(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("SELECT payment.*, workers.workName\n" +
                "FROM payment\n" +
                "         JOIN workers ON payment.EmpId = workers.workId\n" +
                "WHERE payment.transctionId = ?;\n",id);
    }

    @Override
    public ResultSet searchTransacCodeColec(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("SELECT payment.*, collector.Name\n" +
                "FROM payment\n" +
                "         JOIN collector ON payment.EmpId = collector.teaColecId\n" +
                "WHERE payment.transctionId = ?;\n",id);
    }

    @Override
    public HashMap getDetail(String time, String cusId) throws SQLException, ClassNotFoundException {

            ResultSet resultSet = SQLUtil.execute("SELECT orders.unitPrice, orders.quantity, orders.total, tea_types.teaTypeName\n" +
                    "FROM orders\n" +
                    "         JOIN tea_types ON orders.teaTypeId = tea_types.teaTypeId\n" +
                    "WHERE orders.cusId = ? AND orders.time = ? ",cusId,time);
            HashMap myMap1 = new HashMap<>();
            while (resultSet.next()){
                String unitPrice = String.valueOf(resultSet.getDouble("unitPrice"));
                String quantity = String.valueOf(resultSet.getDouble("quantity"));
                String total = String.valueOf(resultSet.getDouble("total"));
                String teaTypeName = resultSet.getString("teaTypeName");

                HashMap myMap = new HashMap<>();
                myMap.put(1,unitPrice);
                myMap.put(2,quantity);
                myMap.put(3,total);
                myMap.put(4,teaTypeName);

                myMap1=myMap;
            }
            return myMap1;
        }

    @Override
    public String getTPrice(String time, String cusId) throws SQLException, ClassNotFoundException {
       ResultSet resultSet = SQLUtil.execute( "SELECT SUM(total) AS NetTotal FROM orders JOIN" +
               " tea_types ON orders.teaTypeId = tea_types.teaTypeId " +
               "WHERE orders.cusId = ? AND orders.time = ?",cusId,time);
            String netTotal = null;
            if(resultSet.next()){
                String netTot = resultSet.getString("NetTotal");
                netTotal=netTot;
            }

            return netTotal;

        }
    }

