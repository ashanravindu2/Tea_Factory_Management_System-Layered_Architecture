package lk.captain.dao.custom;

import lk.captain.dao.CrudDAO;
import lk.captain.db.DbConnection;
import lk.captain.entity.UserReg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserRegDAO extends CrudDAO<UserReg> {
    public boolean getUser(String adminUser,String adminPass) throws Exception;
    public String searchUser(String user ,String pass) throws SQLException, ClassNotFoundException;
}
