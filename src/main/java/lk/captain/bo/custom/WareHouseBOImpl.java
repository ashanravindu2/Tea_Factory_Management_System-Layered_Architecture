package lk.captain.bo.custom;

import lk.captain.dao.DAOFactory;
import lk.captain.dao.custom.TeaTypeDAO;
import lk.captain.dao.custom.WareHouseDAO;
import lk.captain.dao.custom.WoodDAO;

import java.sql.SQLException;

public class WareHouseBOImpl implements WareHouseBO{
    WareHouseDAO wareHouseDAO = (WareHouseDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.WAREHOUSE);

    @Override
    public double getCount() throws SQLException, ClassNotFoundException {
        return wareHouseDAO.getCount();
    }

    @Override
    public boolean isupdated(double reducedTeaTotal) throws SQLException, ClassNotFoundException {
        return wareHouseDAO.isupdated(reducedTeaTotal);
    }
}
