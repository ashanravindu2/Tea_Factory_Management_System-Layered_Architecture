package lk.captain.dao;

import lk.captain.db.DbConnection;
import lk.captain.dto.AddCustomerDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO{
    boolean delete(String id) throws SQLException, ClassNotFoundException;
    boolean save(T dto) throws SQLException, ClassNotFoundException;
    boolean update(T dto) throws SQLException, ClassNotFoundException;
    T search(String id) throws SQLException, ClassNotFoundException;
    ResultSet generateId() throws SQLException, ClassNotFoundException;
     ArrayList<T> getAll() throws SQLException, ClassNotFoundException;
     int searchCount() throws SQLException, ClassNotFoundException;



}
