package lk.captain.bo.custom.impl;

import lk.captain.bo.custom.QueryBO;
import lk.captain.dao.DAOFactory;
import lk.captain.dao.custom.QueryDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class QueryBOImpl implements QueryBO {
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);

    @Override
    public ResultSet searchTransacCode(String id) throws SQLException, ClassNotFoundException {
        return queryDAO.searchTransacCode(id);
    }

    @Override
    public ResultSet searchTransacCodeWorker(String id) throws SQLException, ClassNotFoundException {
        return queryDAO.searchTransacCodeWorker(id);
    }

    @Override
    public ResultSet searchTransacCodeColec(String id) throws SQLException, ClassNotFoundException {
        return queryDAO.searchTransacCodeColec(id);
    }

    @Override
    public HashMap getDetail(String time, String cusId) throws SQLException, ClassNotFoundException {
        return queryDAO.getDetail(time,cusId);
    }

    @Override
    public String getTPrice(String time, String cusId) throws SQLException, ClassNotFoundException {
        return queryDAO.getTPrice(time,cusId);
    }
}
