package lk.captain.bo.custom.impl;

import lk.captain.bo.BOFactory;
import lk.captain.bo.custom.TeaPowderBO;
import lk.captain.dao.DAOFactory;
import lk.captain.dao.custom.TeaLeafEntryDAO;
import lk.captain.dao.custom.TeaPowderDAO;
import lk.captain.dao.custom.WareHouseDAO;
import lk.captain.db.DbConnection;
import lk.captain.dto.TeaPowderDTO;
import lk.captain.dto.TeaPowderGetDTO;
import lk.captain.entity.TeaPowder;
import lk.captain.util.TransactionConnection;
import lk.captain.view.tm.CartTM;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeaPowderBOImpl implements TeaPowderBO {
    TeaPowderDAO teaPowderDAO = (TeaPowderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TEAPOWDER);
    WareHouseDAO wareHouseDAO = (WareHouseDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.WAREHOUSE);

    @Override
    public ArrayList<TeaPowderGetDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<TeaPowder> teaPowderList = teaPowderDAO.getAll();
        ArrayList<TeaPowderGetDTO> teaPowderDTOS = new ArrayList<>();
            for (TeaPowder teaPowder : teaPowderList) {
            teaPowderDTOS.add(new TeaPowderGetDTO(
                    teaPowder.getDate(),
                    teaPowder.getUseTea()
            ));
        }
        return teaPowderDTOS;
    }

    @Override
    public boolean delete(String date) throws SQLException, ClassNotFoundException {
        return teaPowderDAO.delete(date);
    }

    @Override
    public boolean placeOrder(TeaPowderDTO dto) throws SQLException, ClassNotFoundException {
        boolean isSave = saveOrderTm((ArrayList<CartTM>) dto.getTmList());
        boolean isWareHouseUpdate = wareHouseUpdateTm((ArrayList<CartTM>) dto.getTmList());

        if (isSave && isWareHouseUpdate){
            TransactionConnection.getConnection().commit();
            TransactionConnection.setAutoCommitTrue();
            return true;
        }
        return false;
    }
    public  boolean saveOrderTm(ArrayList<CartTM> tmList) throws SQLException, ClassNotFoundException {
        for (CartTM cartTM : tmList) {
            if (!saveOrder(cartTM)) {
                return false;
            }
        }
        return true;
    }

    public  boolean wareHouseUpdateTm(ArrayList<CartTM> tmList) throws SQLException, ClassNotFoundException {
        for (CartTM cartTM : tmList) {
            if (!wareHouseDAO.wareHouseUpdate(cartTM)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean saveOrder(CartTM cartTm) throws SQLException, ClassNotFoundException {
        return teaPowderDAO.saveOrder(cartTm);
    }

    public  boolean updateTeaStock(TeaPowderDTO dto) throws SQLException, ClassNotFoundException {
            boolean isUpdates = updateTeaPowderTm((ArrayList<CartTM>) dto.getTmList());
            boolean isWareHouseUpdate = wareHouseUpdateTm((ArrayList<CartTM>) dto.getTmList());

            if (isUpdates && isWareHouseUpdate){
                TransactionConnection.getConnection().commit();
                TransactionConnection.setAutoCommitTrue();
                return true;
            }
            return false;
    }

    @Override
    public boolean updateTeaPowderTm(ArrayList<CartTM> tmList) throws SQLException, ClassNotFoundException {
            for (CartTM cartTM : tmList) {
                if (!updateTeaPowder(cartTM)) {
                    return false;
                }
            }
            return true;
        }

    @Override
    public boolean updateTeaPowder(CartTM cartTM) throws SQLException, ClassNotFoundException {
       return teaPowderDAO.updateTeaPowder(cartTM);
    }

    @Override
    public double searchDate(String date) throws SQLException, ClassNotFoundException {
        return teaPowderDAO.searchDate(date);
    }
}



