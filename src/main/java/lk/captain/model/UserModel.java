package lk.captain.model;

import lk.captain.Db.DbConnection;
import lk.captain.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

public class UserModel {

    public boolean getUser(String adminUser,String adminPass) throws Exception{
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM user WHERE userName =? AND userPass= ?";


        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,adminUser);
        pstm.setString(2,adminPass);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            return true;
        }
        return false;
    }


}
