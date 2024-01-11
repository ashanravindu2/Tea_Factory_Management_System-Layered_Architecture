package lk.captain.bo.custom.impl;

import lk.captain.bo.custom.PaymentBO;
import lk.captain.dao.DAOFactory;
import lk.captain.dao.custom.AttendenceDAO;
import lk.captain.dao.custom.PaymentDAO;
import lk.captain.dto.PaymentDto;
import lk.captain.entity.Payment;

import java.sql.SQLException;

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);

    @Override
    public boolean addPayement(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(
                new Payment(
                        paymentDto.getTransctionId(),
                        paymentDto.getEmpId(),
                        paymentDto.getStartTime(),
                        paymentDto.getEndDate(),
                        paymentDto.getTransactionDate(),
                        paymentDto.getNetTotal(),
                        paymentDto.getNetWeight(),
                        paymentDto.getFertilizerReduced(),
                        paymentDto.getWorkCount(),
                        paymentDto.getExtraSalary()
                )
        );
    }

    @Override
    public boolean deleteCode(String code) throws SQLException, ClassNotFoundException {
        return paymentDAO.delete(code);
    }
}
