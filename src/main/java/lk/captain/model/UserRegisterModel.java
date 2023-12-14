package lk.captain.model;

import lk.captain.Db.DbConnection;
import lk.captain.dto.TeaCollctorDTO;
import lk.captain.dto.UserRegisterDTO;

import java.sql.*;
import java.time.LocalDate;

public class UserRegisterModel {

    public static boolean registerUser(UserRegisterDTO userRegisterDTO) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO user(userId,userName,userPass,userTele,userAddress,name,userEmail) VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,userRegisterDTO.getUserId());
        pstm.setString(2,userRegisterDTO.getUserName());
        pstm.setString(3,userRegisterDTO.getUserPass());
        pstm.setString(4,userRegisterDTO.getUserTele());
        pstm.setString(5,userRegisterDTO.getUserAddress());
        pstm.setString(6,userRegisterDTO.getName());
        pstm.setString(7,userRegisterDTO.getUserEmail());

        return pstm.executeUpdate() > 0;
    }

    public String generateNextUserId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
try {
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT userId FROM user ORDER BY userId DESC LIMIT 1");
    boolean isExist = resultSet.next();
    if (isExist) {
        String currenUserId = resultSet.getString(1);
        currenUserId = currenUserId.substring(1, currenUserId.length());
        int intId = Integer.parseInt(currenUserId);
        intId = intId + 1;

        if (intId < 10) {
            return "U00" + intId;
        } else if (intId < 100) {
            return "U0" + intId;
        } else {
            return "U" + intId;
        }

    } else {
        return "U001";
    }
    }catch (SQLException e){
    e.printStackTrace();
    }
            return null;
    }

    public String searchUser(String user ,String pass) throws SQLException {
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
    }
}
