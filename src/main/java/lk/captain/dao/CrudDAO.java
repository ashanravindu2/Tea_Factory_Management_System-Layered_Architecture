package lk.captain.dao;

import java.sql.SQLException;

public interface CrudDAO<T> extends SuperDAO{
    boolean delete(String id) throws SQLException, ClassNotFoundException;
}
