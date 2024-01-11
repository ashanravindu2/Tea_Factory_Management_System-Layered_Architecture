package lk.captain.bo.custom.impl;

import lk.captain.bo.custom.StoreDetailBO;
import lk.captain.dao.DAOFactory;
import lk.captain.dao.custom.StoreDetailDAO;
import lk.captain.dao.custom.WareHouseDAO;
import lk.captain.dto.StoreDetailsDTO;
import lk.captain.entity.StoreDetails;
import lk.captain.util.TransactionConnection;
import lk.captain.view.tm.OrderCartTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreDetailBOImpl implements StoreDetailBO {

    StoreDetailDAO storeDetailDAO = (StoreDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STOREDETAIL);
    WareHouseDAO wareHouseDAO = (WareHouseDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.WAREHOUSE);
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return storeDetailDAO.delete(id);
    }

    @Override
    public StoreDetailsDTO search(String id) throws SQLException, ClassNotFoundException {
        StoreDetails storeDetails = storeDetailDAO.search(id);
        StoreDetailsDTO storeDetailsDTO = new StoreDetailsDTO(
                storeDetails.getWareHouseId(),
                storeDetails.getTeaTypeId(),
                storeDetails.getDate(),
                storeDetails.getQuantity()
        );
        return storeDetailsDTO;
    }

    @Override
    public ArrayList<StoreDetailsDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<StoreDetails> storeDetails = storeDetailDAO.getAll();
        ArrayList<StoreDetailsDTO> storeDetailsDTOS = new ArrayList<>();

        for (StoreDetails storeDetail : storeDetails) {
            storeDetailsDTOS.add(new StoreDetailsDTO(
                    storeDetail.getWareHouseId(),
                    storeDetail.getTeaTypeId(),
                    storeDetail.getDate(),
                    storeDetail.getQuantity()
            ));
        }
        return storeDetailsDTOS;
    }

    @Override
    public boolean save(StoreDetailsDTO dto) throws SQLException, ClassNotFoundException {
        boolean isStored;
        boolean isWareHouseUpdated = false;
        isStored = storeDetailDAO.save(new StoreDetails(
                dto.getWareHouseId(),
                dto.getTeaTypeId(),
                dto.getDate(),
                dto.getQuantity()
        ));

        isWareHouseUpdated= wareHouseDAO.isupdated(dto.getQuantity());

        if (isStored && isWareHouseUpdated) {
            TransactionConnection.getConnection().commit();
            TransactionConnection.setAutoCommitTrue();
            return true;
        }
        return false;
        }
    }

