package lk.captain.bo.custom;

import lk.captain.bo.SuperBO;
import lk.captain.dao.SQLUtil;
import lk.captain.dto.FuelMaterialDTO;
import lk.captain.dto.StoreDetailsDTO;
import lk.captain.entity.StoreDetails;
import lk.captain.view.tm.OrderCartTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface StoreDetailBO extends SuperBO {

     boolean delete(String id) throws SQLException, ClassNotFoundException;
     StoreDetailsDTO search(String id) throws SQLException, ClassNotFoundException;
     ArrayList<StoreDetailsDTO> getAll() throws SQLException, ClassNotFoundException;
     boolean save(StoreDetailsDTO dto) throws SQLException, ClassNotFoundException;

}