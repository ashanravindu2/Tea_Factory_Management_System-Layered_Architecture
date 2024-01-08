package lk.captain.dao;

import lk.captain.db.DbConnection;
import lk.captain.dto.AddCustomerDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO{
    boolean delete(String id) throws SQLException, ClassNotFoundException;
    boolean save(T dto) throws SQLException, ClassNotFoundException;
    boolean updateCustomer(T dto) throws SQLException, ClassNotFoundException;
    T searchCusId(String id) throws SQLException, ClassNotFoundException;
    String generateCusId() throws SQLException, ClassNotFoundException;
     ArrayList<T> getAllCus() throws SQLException, ClassNotFoundException;



}
