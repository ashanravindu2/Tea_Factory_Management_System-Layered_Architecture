package lk.captain.dao.custom.impl;

import lk.captain.dao.SQLUtil;
import lk.captain.dao.custom.TeaLeafEntryDAO;
import lk.captain.db.DbConnection;
import lk.captain.dto.TeaLeafEntryDTO;
import lk.captain.entity.AddCustomer;
import lk.captain.entity.TeaLeaf;
import lk.captain.entity.TeaLeafEntry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeaLeafEntryDAOImpl implements TeaLeafEntryDAO {

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean save(TeaLeafEntry dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO tea_leafentry(supplierId,teaColecId,grosWeight,waterCon,netWeight,date) VALUES(?, ?, ?, ?, ?, ?)",
                dto.getSupplierId(), dto.getTeaColecId(), dto.getGrosWeight(), dto.getWaterCon(), dto.getNetWeight(), dto.getDate());

    }

    @Override
    public boolean update(TeaLeafEntry dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "UPDATE tea_leafentry SET teaColecId = ? ,grosWeight =?,waterCon=?,netWeight=?,date=? WHERE supplierId = ?",
                dto.getTeaColecId(),dto.getGrosWeight(),dto.getWaterCon(),dto.getNetWeight(),dto.getDate(),dto.getSupplierId());
    }

    @Override
    public TeaLeafEntry search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ResultSet generateId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<TeaLeafEntry> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM tea_leafentry");
        ArrayList<TeaLeafEntry> teaLeafEntries = new ArrayList<>();

        while (rst.next()) {
            TeaLeafEntry teaLeafEntry = new TeaLeafEntry(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    rst.getString(6)
            );
            teaLeafEntries.add(teaLeafEntry);
        }
        return teaLeafEntries;
    }

    @Override
    public int searchCount() throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public double getNetTotal(String date) throws SQLException, ClassNotFoundException {
       ResultSet resultSet = SQLUtil.execute("SELECT SUM(netWeight) AS total FROM tea_leafEntry WHERE date = ?",date);

            if(resultSet.next()) {
                double netTotal =resultSet.getDouble("total");
                return netTotal;
            }
            return 0;
        }

    @Override
    public double getNetValue(String date1, String date2, String suppId) throws SQLException, ClassNotFoundException {
       ResultSet resultSet = SQLUtil.execute("SELECT SUM(netWeight) AS NETWEIGHT\n" +
                    "FROM tea_leafEntry\n" +
                    "WHERE supplierId = ?\n" +
                    "  AND DATE(date) BETWEEN ? AND ?",suppId,date1,date2);
            if(resultSet.next()) {
                double netTotal =resultSet.getDouble("NETWEIGHT");
                return netTotal;
            }

            return 0;
        }

    @Override
    public TeaLeafEntry seacrhTeaLeaf(String id, String date) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT\n" +
                "    SUM(grosWeight) AS totalGrosWeight,\n" +
                "    SUM(waterCon) AS totalWaterCon,\n" +
                "    SUM(netWeight) AS totalNetWeight,\n" +
                "    tea_leafentry.date AS date,\n" +
                "    tea_leafentry.supplierId,\n" +
                "    tea_leafentry.teaColecId\n" +
                "\n" +
                "FROM\n" +
                "    tea_leafentry\n" +
                "WHERE\n" +
                "        tea_leafentry.supplierId = ? \n" +
                "  AND DATE(tea_leafentry.date) = ? \n" +
                "GROUP BY\n" +
                "    tea_leafentry.supplierId,\n" +
                "    tea_leafentry.teaColecId,\n" +
                "tea_leafentry.date;", id, date);
        if (resultSet.next()) {
            TeaLeafEntry teaLeafEntry = new TeaLeafEntry(
                    resultSet.getString("supplierId"),
                    resultSet.getString("teaColecId"),
                    resultSet.getDouble("totalGrosWeight"),
                    resultSet.getDouble("totalWaterCon"),
                    resultSet.getDouble("totalNetWeight"),
                    resultSet.getString("date")
            );
            return teaLeafEntry;
        }
        return null;
    }

    @Override
    public boolean deleteTeaLeaf(String suppId, String date) throws SQLException, ClassNotFoundException {
       return SQLUtil.execute("DELETE FROM tea_leafentry WHERE supplierId = ? AND date = ? ",suppId,date);

    }

    @Override
    public double getReducedTotal(String date) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(reducedWeight) AS total FROM tea_leafEntry WHERE date = ?",date);

            if(resultSet.next()) {
                double netTotal =resultSet.getDouble("total");
                return netTotal;
            }
            return 0;
    }

}


