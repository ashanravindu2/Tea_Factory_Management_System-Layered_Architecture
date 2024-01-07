package lk.captain.model;

import lk.captain.db.DbConnection;
import lk.captain.dto.PaymentDto;

import java.sql.*;

public class PaymentModel {

    public boolean addPayement(PaymentDto paymentDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO payment(transctionId,EmpId,startTime,endDate,transactionDate,netTotal,netWeight ,fertilizerReduced,workCount,extraSalary) VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,paymentDto.getTransctionId());
        pstm.setString(2,paymentDto.getEmpId());
        pstm.setDate(3, Date.valueOf(paymentDto.getStartTime()));
        pstm.setString(4, paymentDto.getEndDate());
        pstm.setString(5, paymentDto.getTransactionDate());
        pstm.setDouble(6, Double.parseDouble(String.valueOf(paymentDto.getNetTotal())));
        pstm.setDouble(7, Double.parseDouble(String.valueOf(paymentDto.getNetWeight())));
        pstm.setDouble(8, Double.parseDouble(String.valueOf(paymentDto.getFertilizerReduced())));
        pstm.setInt(9,paymentDto.getWorkCount());
        pstm.setDouble(10,paymentDto.getExtraSalary());

        return pstm.executeUpdate()>0;
    }

    public ResultSet searchTransacCode(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT payment.*, suppliers.suppName\n" +
                "FROM payment\n" +
                "         JOIN suppliers ON payment.EmpId = suppliers.supplierId\n" +
                "WHERE payment.transctionId = ?;\n";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        return resultSet;
    }

    public boolean deleteCode(String code) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM payment WHERE transctionId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, code);

        return pstm.executeUpdate() > 0;
    }
    public ResultSet searchTransacCodeWorker(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT payment.*, workers.workName\n" +
                "FROM payment\n" +
                "         JOIN workers ON payment.EmpId = workers.workId\n" +
                "WHERE payment.transctionId = ?;\n";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        return resultSet;
    }
    public ResultSet searchTransacCodeColec(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT payment.*, collector.Name\n" +
                "FROM payment\n" +
                "         JOIN collector ON payment.EmpId = collector.teaColecId\n" +
                "WHERE payment.transctionId = ?;\n";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        return resultSet;
    }

}
