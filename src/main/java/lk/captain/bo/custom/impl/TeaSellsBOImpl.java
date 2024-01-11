package lk.captain.bo.custom.impl;

import lk.captain.bo.BOFactory;
import lk.captain.bo.custom.AddCustomerBO;
import lk.captain.bo.custom.StoreDetailBO;
import lk.captain.bo.custom.TeaSellsBO;
import lk.captain.dao.DAOFactory;
import lk.captain.dao.custom.StoreDetailDAO;
import lk.captain.dao.custom.TeaSellsDAO;
import lk.captain.dao.custom.TeaTypeDAO;
import lk.captain.db.DbConnection;
import lk.captain.dto.TeaSellsDTO;
import lk.captain.util.TransactionConnection;
import lk.captain.view.tm.OrderCartTM;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeaSellsBOImpl implements TeaSellsBO {
    TeaSellsDAO teaSellsDAO = (TeaSellsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TEASELLS);
    StoreDetailDAO storeDetailDAO = (StoreDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STOREDETAIL);

    @Override
    public ResultSet generateNextOrderId() throws SQLException, ClassNotFoundException {
        return teaSellsDAO.generateId();
    }

    @Override
    public boolean placeOrder(TeaSellsDTO pDto) throws SQLException, ClassNotFoundException {
                boolean isOrderSaved = saveOrder(pDto.getOrderId(), pDto.getCusId(), pDto.getDate(), pDto.getTeaTypeName(), pDto.getTime(), (OrderCartTM) pDto.getTmList());
                boolean isTeaTypesStockUpdated =updateStock(pDto.getTmList());

                if (isOrderSaved && isTeaTypesStockUpdated) {
                    TransactionConnection.getConnection().commit();
                    TransactionConnection.setAutoCommitTrue();
                    return true;
                }
                    return false;
        }

    public boolean updateStock(List<OrderCartTM> tmList) throws SQLException, ClassNotFoundException {
        for (OrderCartTM cartTm : tmList) {
            if(!storeDetailDAO.updateTM(cartTm)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean saveOrderTm(String orderId, String cusId, String date, String teaTypeName, String time, ArrayList<OrderCartTM> tmList) throws SQLException, ClassNotFoundException {
        for (OrderCartTM cartTm : tmList) {
            if (!saveOrder(orderId, cusId, date, teaTypeName, time, cartTm)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean saveOrder(String orderId, String cusId, String date, String teaTypeName, String time, OrderCartTM orderCartTM) throws SQLException, ClassNotFoundException {
        return teaSellsDAO.saveOrder(orderId, cusId, date, teaTypeName, time, orderCartTM);
    }

    /*@Override
    public boolean saveOrder(String orderId, String cusId, String date, String teaTypeName, String time, ArrayList<OrderCartTM> tmLis) throws SQLException, ClassNotFoundException {
        return teaSellsDAO.saveOrder(orderId, cusId, date, teaTypeName, time, tmLis);
    }*/


}