package lk.captain.bo.custom;

import lk.captain.bo.SuperBO;
import lk.captain.dao.SQLUtil;
import lk.captain.db.DbConnection;
import lk.captain.dto.TeaPowderDTO;
import lk.captain.dto.TeaPowderGetDTO;
import lk.captain.entity.TeaPowder;
import lk.captain.view.tm.CartTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TeaPowderBO extends SuperBO {

     ArrayList<TeaPowderGetDTO> getAll() throws SQLException, ClassNotFoundException;
     boolean delete(String id) throws SQLException, ClassNotFoundException;
     boolean placeOrder(TeaPowderDTO dto) throws SQLException, ClassNotFoundException;
     boolean saveOrderTm(ArrayList<CartTM> tmList) throws SQLException, ClassNotFoundException;
     boolean wareHouseUpdateTm(ArrayList<CartTM> tmList) throws SQLException, ClassNotFoundException;
     boolean saveOrder(CartTM cartTm) throws SQLException, ClassNotFoundException;
     boolean updateTeaStock(TeaPowderDTO dto) throws SQLException, ClassNotFoundException;
     boolean updateTeaPowderTm(ArrayList<CartTM> tmList) throws SQLException, ClassNotFoundException;
     boolean updateTeaPowder(CartTM cartTM) throws SQLException, ClassNotFoundException;
     double searchDate(String date) throws SQLException, ClassNotFoundException;
}
