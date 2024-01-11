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
    public ArrayList<TeaPowderGetDTO> getAll() throws SQLException, ClassNotFoundException;
    public boolean delete(String id) throws SQLException, ClassNotFoundException;
public  boolean placeOrder(TeaPowderDTO dto) throws SQLException, ClassNotFoundException;
    public  boolean saveOrderTm(ArrayList<CartTM> tmList) throws SQLException, ClassNotFoundException;
    public  boolean wareHouseUpdateTm(ArrayList<CartTM> tmList) throws SQLException, ClassNotFoundException;
    public boolean saveOrder(CartTM cartTm) throws SQLException, ClassNotFoundException;
    public  boolean updateTeaStock(TeaPowderDTO dto) throws SQLException, ClassNotFoundException;
    public  boolean updateTeaPowderTm(ArrayList<CartTM> tmList) throws SQLException, ClassNotFoundException;
    public  boolean updateTeaPowder(CartTM cartTM) throws SQLException, ClassNotFoundException;
    public double searchDate(String date) throws SQLException, ClassNotFoundException;
}
