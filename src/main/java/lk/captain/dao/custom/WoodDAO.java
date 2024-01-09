package lk.captain.dao.custom;

import lk.captain.dao.CrudDAO;
import lk.captain.db.DbConnection;
import lk.captain.entity.Wood;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface WoodDAO extends CrudDAO<Wood> {
    public ResultSet getAllAvalable() throws SQLException, ClassNotFoundException;
    public boolean usedUpdateWood(String id, double used) throws SQLException, ClassNotFoundException;
}
