package lk.captain.dao.custom.impl;

import lk.captain.dao.SQLUtil;
import lk.captain.dao.custom.PaymentDAO;
import lk.captain.db.DbConnection;
import lk.captain.dto.PaymentDto;
import lk.captain.entity.Payment;

import java.sql.*;
import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean save(Payment dto) throws SQLException, ClassNotFoundException {
       return SQLUtil.execute(
        "INSERT INTO payment(transctionId,EmpId,startTime,endDate,transactionDate,netTotal,netWeight ,fertilizerReduced,workCount,extraSalary) VALUES (?,?,?,?,?,?,?,?,?,?)",
               dto.getTransctionId(),dto.getEmpId(),dto.getStartTime(),dto.getEndDate(),dto.getTransactionDate(),dto.getNetTotal(),dto.getNetWeight(),dto.getFertilizerReduced(),dto.getWorkCount(),dto.getExtraSalary());

    }

    @Override
    public boolean update(Payment dto) throws SQLException, ClassNotFoundException {
       return SQLUtil.execute("DELETE FROM payment WHERE transctionId = ?",dto.getTransctionId());
    }

    @Override
    public Payment search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ResultSet generateId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Payment> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public int searchCount() throws SQLException, ClassNotFoundException {
        return 0;
    }
}
