package lk.captain.bo.custom;

import lk.captain.bo.SuperBO;
import lk.captain.db.DbConnection;
import lk.captain.dto.SupplierManageDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface TeaSupplierBO extends SuperBO {

    public boolean supplierSave(SupplierManageDTO supplierManageDTO) throws SQLException, ClassNotFoundException;

    public String generateSupId() throws SQLException, ClassNotFoundException;

    public List<SupplierManageDTO> getAllTeaSupp() throws SQLException, ClassNotFoundException;

    public boolean deleteSupplier(String suppId) throws SQLException, ClassNotFoundException;

    public boolean updateSupplier(SupplierManageDTO dto) throws SQLException, ClassNotFoundException;

    public SupplierManageDTO searchSupplierId(String id) throws SQLException, ClassNotFoundException;

}
