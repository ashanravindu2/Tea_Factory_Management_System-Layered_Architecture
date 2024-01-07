package lk.captain.model;

import lk.captain.db.DbConnection;
import lk.captain.dto.TeaLeafEntryDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeaLeafModel {
    public boolean tealeafManage(TeaLeafEntryDTO teaLeafEntryDTO) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO tea_leafentry(supplierId,teaColecId,grosWeight,waterCon,netWeight,date) VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,teaLeafEntryDTO.getSupplierId() );
        pstm.setString(2,teaLeafEntryDTO.getTeaColecId());
        pstm.setDouble(3,teaLeafEntryDTO.getGrosWeight());
        pstm.setDouble(4,teaLeafEntryDTO.getWaterCon());
        pstm.setDouble(5,teaLeafEntryDTO.getNetWeight());
        pstm.setDate(6, Date.valueOf(teaLeafEntryDTO.getDate()));


        return pstm.executeUpdate() > 0;
    }

    public List<TeaLeafEntryDTO> getAllDetail() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM tea_leafentry";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<TeaLeafEntryDTO> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String supplierId = resultSet.getString(1);
            String teaColecId = resultSet.getString(2);
            double grosWeight = resultSet.getDouble(3);
            double waterCon = resultSet.getDouble(4);
            double netWeight = resultSet.getDouble(5);
            String date = String.valueOf(resultSet.getDate(6));


            var dto = new TeaLeafEntryDTO(supplierId, teaColecId,grosWeight, waterCon, netWeight,date);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public double getNetTotal(String date) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT SUM(netWeight) AS total FROM tea_leafEntry WHERE date = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setDate(1, Date.valueOf(String.valueOf(date)));

        ResultSet resultSet = pstm.executeQuery();

       // TeaLeafEntryDTO dto = null;

        if(resultSet.next()) {
            double netTotal =resultSet.getDouble("total");
            return netTotal;
        }

        return 0;
    }


    public double getReducedTotal(String date) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT SUM(reducedWeight) AS total FROM tea_leafEntry WHERE date = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setDate(1, Date.valueOf(String.valueOf(date)));

        ResultSet resultSet = pstm.executeQuery();

        // TeaLeafEntryDTO dto = null;

        if(resultSet.next()) {
            double netTotal =resultSet.getDouble("total");
            return netTotal;
        }

        return 0;
    }


    public double getNetValue(String date1, String date2,String suppId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT SUM(netWeight) AS NETWEIGHT\n" +
                "FROM tea_leafEntry\n" +
                "WHERE supplierId = ?\n" +
                "  AND DATE(date) BETWEEN ? AND ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,suppId);
        pstm.setDate(2, Date.valueOf(date1));
        pstm.setDate(3, Date.valueOf(date2));

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            double netTotal =resultSet.getDouble("NETWEIGHT");
            return netTotal;
        }

        return 0;
    }
    public TeaLeafEntryDTO seacrhTeaLeaf(String id,String date) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT\n" +
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
                "tea_leafentry.date;";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);
        pstm.setString(2, date);

        ResultSet resultSet = pstm.executeQuery();

        TeaLeafEntryDTO dto = null;

        if(resultSet.next()) {
            String suppId =resultSet.getString(5);
            String colecId = resultSet.getString(6);
            double grosWeight = resultSet.getDouble(1);
            double waterContent = resultSet.getDouble(2);
            double netWeight = resultSet.getDouble(3);
            String dates = resultSet.getString(4);

            dto = new TeaLeafEntryDTO(suppId, colecId, grosWeight, waterContent,netWeight,dates);
        }

        return dto;
    }
    public boolean deleteTeaLeaf(String suppId,String date) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM tea_leafentry WHERE supplierId = ? AND date = ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, suppId);
        pstm.setString(2, date);

        return pstm.executeUpdate() > 0;
    }
    public boolean updateTeaLeaf(TeaLeafEntryDTO dto) throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE tea_leafentry SET teaColecId = ? ,grosWeight =?,waterCon=?,netWeight=?,date=? WHERE supplierId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getTeaColecId());
        pstm.setDouble(2, dto.getGrosWeight());
        pstm.setDouble(3, dto.getWaterCon());
        pstm.setDouble(4, dto.getNetWeight());
        pstm.setString(5, dto.getDate());
        pstm.setString(6, dto.getSupplierId());

        return pstm.executeUpdate() > 0;
    }
}
