package lk.captain.bo.custom;

import lk.captain.bo.SuperBO;
import lk.captain.dao.SQLUtil;
import lk.captain.dto.WoodMaterialDTO;
import lk.captain.entity.Wood;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface WoodBO extends SuperBO {

    public boolean delete(String id) throws SQLException, ClassNotFoundException;


    public boolean save(WoodMaterialDTO dto) throws SQLException, ClassNotFoundException;

    public boolean update(WoodMaterialDTO dto) throws SQLException, ClassNotFoundException;

    public WoodMaterialDTO search(String id) throws SQLException, ClassNotFoundException;


    public ResultSet generateId() throws SQLException, ClassNotFoundException;


    public ArrayList<WoodMaterialDTO> getAll() throws SQLException, ClassNotFoundException;


    public int searchCount() throws SQLException, ClassNotFoundException;
}
