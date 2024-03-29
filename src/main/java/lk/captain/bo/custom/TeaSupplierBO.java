package lk.captain.bo.custom;

import lk.captain.bo.SuperBO;
import lk.captain.db.DbConnection;
import lk.captain.dto.SupplierManageDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface TeaSupplierBO extends SuperBO {

     boolean supplierSave(SupplierManageDTO supplierManageDTO) throws SQLException, ClassNotFoundException;
     ResultSet generateSupId() throws SQLException, ClassNotFoundException;
     List<SupplierManageDTO> getAllTeaSupp() throws SQLException, ClassNotFoundException;
     boolean deleteSupplier(String suppId) throws SQLException, ClassNotFoundException;
     boolean updateSupplier(SupplierManageDTO dto) throws SQLException, ClassNotFoundException;
     SupplierManageDTO searchSupplierId(String id) throws SQLException, ClassNotFoundException;

}
