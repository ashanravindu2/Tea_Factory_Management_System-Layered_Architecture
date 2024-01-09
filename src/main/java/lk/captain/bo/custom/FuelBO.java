package lk.captain.bo.custom;

import lk.captain.bo.SuperBO;
import lk.captain.db.DbConnection;
import lk.captain.dto.FuelMaterialDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface FuelBO extends SuperBO {
     boolean fuelSave(FuelMaterialDTO fuelMaterialDTO) throws SQLException, ClassNotFoundException;
     public boolean deleteFuel(String id) throws SQLException, ClassNotFoundException;
     public FuelMaterialDTO searchFuelId(String id) throws SQLException, ClassNotFoundException;
     public boolean update(FuelMaterialDTO dto) throws SQLException, ClassNotFoundException;
     public List<FuelMaterialDTO> getAllFuel() throws SQLException, ClassNotFoundException;


}
