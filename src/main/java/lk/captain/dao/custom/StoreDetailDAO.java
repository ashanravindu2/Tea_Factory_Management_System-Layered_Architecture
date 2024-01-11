package lk.captain.dao.custom;

import lk.captain.dao.CrudDAO;
import lk.captain.entity.StoreDetails;
import lk.captain.view.tm.OrderCartTM;

import java.sql.SQLException;
import java.util.List;

public interface StoreDetailDAO extends CrudDAO<StoreDetails> {

    boolean updateTM(OrderCartTM orderCartTM) throws SQLException, ClassNotFoundException;
}
