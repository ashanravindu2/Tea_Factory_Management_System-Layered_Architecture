package lk.captain.bo.custom.impl;

import lk.captain.bo.SuperBO;
import lk.captain.bo.custom.UserRegBO;
import lk.captain.dao.DAOFactory;
import lk.captain.dao.custom.TeaTypeDAO;
import lk.captain.dao.custom.UserRegDAO;
import lk.captain.dto.UserRegisterDTO;
import lk.captain.entity.UserReg;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRegBOImpl implements UserRegBO {
    UserRegDAO userRegDAO = (UserRegDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USERREG);
    @Override
    public boolean registerUser(UserRegisterDTO userRegisterDTO) throws SQLException, ClassNotFoundException {
        return userRegDAO.save(new UserReg(
                userRegisterDTO.getUserId(),
                userRegisterDTO.getUserName(),
                userRegisterDTO.getUserPass(),
                userRegisterDTO.getUserTele(),
                userRegisterDTO.getUserAddress(),
                userRegisterDTO.getName(),
                userRegisterDTO.getUserEmail()
        ));
    }

    @Override
    public ResultSet generateNextUserId() throws SQLException, ClassNotFoundException {
        return userRegDAO.generateId();
    }

    @Override
    public UserReg search(String id) throws SQLException, ClassNotFoundException {

        return null;
    }

    @Override
    public boolean getUser(String adminUser, String adminPass) throws Exception {
        return userRegDAO.getUser(adminUser,adminPass);
    }

    @Override
    public String searchUser(String user, String pass) throws SQLException, ClassNotFoundException {
        return userRegDAO.searchUser(user,pass);
    }

}
