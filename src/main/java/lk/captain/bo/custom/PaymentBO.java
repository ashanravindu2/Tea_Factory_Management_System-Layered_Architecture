package lk.captain.bo.custom;

import lk.captain.bo.SuperBO;
import lk.captain.db.DbConnection;
import lk.captain.dto.PaymentDto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PaymentBO extends SuperBO {
    public boolean addPayement(PaymentDto paymentDto) throws SQLException, ClassNotFoundException;
    public boolean deleteCode(String code) throws SQLException, ClassNotFoundException;
}
