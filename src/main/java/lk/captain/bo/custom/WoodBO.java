package lk.captain.bo.custom;

import lk.captain.bo.SuperBO;
import lk.captain.dao.SQLUtil;
import lk.captain.db.DbConnection;
import lk.captain.dto.WoodMaterialDTO;
import lk.captain.entity.Wood;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface WoodBO extends SuperBO {

     boolean delete(String id) throws SQLException, ClassNotFoundException;
     boolean save(WoodMaterialDTO dto) throws SQLException, ClassNotFoundException;
     boolean update(WoodMaterialDTO dto) throws SQLException, ClassNotFoundException;
     WoodMaterialDTO search(String id) throws SQLException, ClassNotFoundException;
     ResultSet generateId() throws SQLException, ClassNotFoundException;
     ArrayList<WoodMaterialDTO> getAll() throws SQLException, ClassNotFoundException;
     int searchCount() throws SQLException, ClassNotFoundException;
     ResultSet getAllAvalable() throws SQLException, ClassNotFoundException;
     boolean usedUpdateWood(String id, double used) throws SQLException, ClassNotFoundException;
}
