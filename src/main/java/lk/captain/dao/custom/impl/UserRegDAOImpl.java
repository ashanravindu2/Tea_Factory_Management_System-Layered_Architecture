package lk.captain.dao.custom.impl;

import lk.captain.dao.SQLUtil;
import lk.captain.dao.custom.UserRegDAO;
import lk.captain.db.DbConnection;
import lk.captain.dto.UserRegisterDTO;
import lk.captain.entity.UserReg;

import java.sql.*;
import java.util.ArrayList;

public class UserRegDAOImpl implements UserRegDAO {
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean save(UserReg dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO user(userId,userName,userPass,userTele,userAddress,name,userEmail) VALUES(?, ?, ?, ?, ?, ?, ?)"
                , dto.getUserId(), dto.getUserName(), dto.getUserPass(), dto.getUserTele(), dto.getUserAddress(), dto.getName(), dto.getUserEmail());
    }

    @Override
    public boolean update(UserReg dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public UserReg search(String id) throws SQLException, ClassNotFoundException {

        return null;
    }

    @Override
    public ResultSet generateId() throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("SELECT userId FROM user ORDER BY userId DESC LIMIT 1");
    }

    @Override
    public ArrayList<UserReg> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public int searchCount() throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public boolean getUser(String adminUser, String adminPass) throws Exception {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM user WHERE userName =? AND userPass= ?", adminUser, adminPass);
        if (resultSet.next()) {
            return true;
        }
        return false;
    }

    @Override
    public String searchUser(String user, String pass) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT name FROM user WHERE userName = ? AND userPass = ?", user, pass);
            String name = "null";
            if (resultSet.next()) {
                String names = resultSet.getString(1);
                name = names;
            }
            return name;
    }
}



  /*  public String searchUser(String user ,String pass) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT name FROM user WHERE userName = ? AND userPass = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, user);
        pstm.setString(2, pass);

        ResultSet resultSet = pstm.executeQuery();

        String name = "null";
        if (resultSet.next()) {
            String names = resultSet.getString(1);
            name = names;
        }
        return name;
    }*/

