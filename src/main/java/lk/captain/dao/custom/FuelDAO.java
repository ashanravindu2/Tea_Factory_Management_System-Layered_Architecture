package lk.captain.dao.custom;

import lk.captain.dao.CrudDAO;
import lk.captain.dao.SuperDAO;
import lk.captain.db.DbConnection;
import lk.captain.entity.Fuel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface FuelDAO extends CrudDAO<Fuel> {

     boolean usedUpdateFuel(String id ,double liter) throws SQLException, ClassNotFoundException;
     ResultSet getAllAvalable() throws SQLException, ClassNotFoundException;

}
