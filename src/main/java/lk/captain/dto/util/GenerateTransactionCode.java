package lk.captain.dto.util;

import lk.captain.db.DbConnection;
import lk.captain.dto.PaymenSuppDetailGetDTO;
import lk.captain.dto.PaymentWorkerDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenerateTransactionCode {
    public static String generateTransacId()throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet=statement.executeQuery("SELECT transctionId FROM payment ORDER BY transctionId DESC LIMIT 1");
            boolean isExist = resultSet.next();

            if(isExist){
                String oldCode = resultSet.getString(1);
                oldCode =oldCode.substring(1,oldCode.length());
                int intId =Integer.parseInt(oldCode);
                intId =intId+1;

                if (intId <10){
                    return "T00" +intId;
                } else if (intId <100) {
                    return "T0"+intId;

                }else {
                    return "T"+intId;
                }
            }else {
                return "T001";
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<PaymenSuppDetailGetDTO> getAllDetail() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT T.transctionId,T.transactionDate, T.netTotal, T.netWeight, E.suppName\n" +
                "FROM payment T\n" +
                "         JOIN suppliers E ON T.EmpId = E.supplierId\n" +
                "WHERE T.EmpId LIKE 'S%'";

        List<PaymenSuppDetailGetDTO> dtoList = new ArrayList<>();

        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String transctionId =resultSet.getString(1);
            String transactionDate = resultSet.getString(2);
            double netTotal = resultSet.getDouble(3);
            double netWeight = resultSet.getDouble(4);
            String EmpId = resultSet.getString(5);

            var dto = new PaymenSuppDetailGetDTO(transctionId,transactionDate,netTotal,netWeight,EmpId);
            dtoList.add(dto);
        }


        return dtoList;
    }
    public List<PaymentWorkerDTO> getAllDetailworker() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();



        String sql = "SELECT P.transctionId,P.transactionDate, P.netTotal, P.workCount,P.extraSalary ,W.workName FROM payment P JOIN workers W ON P.EmpId = W.workId WHERE P.EmpId LIKE 'W%'";

        List<PaymentWorkerDTO> dtoList = new ArrayList<>();

        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String transctionId =resultSet.getString(1);
            String transactionDate = resultSet.getString(2);
            double netTotal = resultSet.getDouble(3);
            int workCount = resultSet.getInt(4);
            double extraSalary = resultSet.getDouble(5);
            String workName = resultSet.getString(6);

            var dto = new PaymentWorkerDTO(transctionId,transactionDate,workCount,workName,extraSalary,netTotal);
            dtoList.add(dto);
        }


        return dtoList;
    }
    public List<PaymentWorkerDTO> getAllDetailTeaColec() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();



        String sql = "SELECT P.transctionId,P.transactionDate, P.netTotal, P.workCount,P.extraSalary ,K.Name FROM payment P JOIN collector K ON P.EmpId = K.teaColecId WHERE P.EmpId LIKE 'K%'";

        List<PaymentWorkerDTO> dtoList = new ArrayList<>();

        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String transctionId =resultSet.getString(1);
            String transactionDate = resultSet.getString(2);
            double netTotal = resultSet.getDouble(3);
            int workCount = resultSet.getInt(4);
            double extraSalary = resultSet.getDouble(5);
            String workName = resultSet.getString(6);

            var dto = new PaymentWorkerDTO(transctionId,transactionDate,workCount,workName,extraSalary,netTotal);
            dtoList.add(dto);
        }


        return dtoList;
    }
}
