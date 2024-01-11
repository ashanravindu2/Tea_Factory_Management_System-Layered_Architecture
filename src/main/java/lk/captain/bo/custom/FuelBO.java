package lk.captain.bo.custom;

import lk.captain.bo.SuperBO;
import lk.captain.db.DbConnection;
import lk.captain.dto.FuelMaterialDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface FuelBO extends SuperBO {

     boolean fuelSave(FuelMaterialDTO fuelMaterialDTO) throws SQLException, ClassNotFoundException;
      boolean deleteFuel(String id) throws SQLException, ClassNotFoundException;
      FuelMaterialDTO searchFuelId(String id) throws SQLException, ClassNotFoundException;
      boolean update(FuelMaterialDTO dto) throws SQLException, ClassNotFoundException;
      List<FuelMaterialDTO> getAllFuel() throws SQLException, ClassNotFoundException;
      ResultSet generateFuelId()throws SQLException, ClassNotFoundException;
     boolean usedUpdateFuel(String id ,double liter) throws SQLException, ClassNotFoundException;
      ResultSet getAllAvalable() throws SQLException, ClassNotFoundException;

}
