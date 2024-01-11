package lk.captain.bo.custom;

import lk.captain.bo.SuperBO;
import lk.captain.dao.SQLUtil;
import lk.captain.db.DbConnection;
import lk.captain.dto.UserRegisterDTO;
import lk.captain.entity.UserReg;

import java.sql.*;

public interface UserRegBO extends SuperBO {
    public  boolean registerUser(UserRegisterDTO userRegisterDTO) throws SQLException, ClassNotFoundException;
    public ResultSet generateNextUserId() throws SQLException, ClassNotFoundException;
    public UserReg search(String id) throws SQLException, ClassNotFoundException;
    public boolean getUser(String adminUser, String adminPass) throws Exception;
    public String searchUser(String user ,String pass) throws SQLException, ClassNotFoundException;

}
