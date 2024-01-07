package lk.captain.bo;

import lk.captain.dto.AddCustomerDTO;

import java.sql.*;
import java.util.List;

public interface AddCustomerBO extends SuperBO{
     boolean addCustomer(AddCustomerDTO addCustomerDTO) throws SQLException;
     String generateCusId()throws SQLException;
     boolean deleteCus(String cusIds) throws SQLException, ClassNotFoundException;
     boolean updateCustomer(AddCustomerDTO dto) throws SQLException;
     AddCustomerDTO searchCusId(String id) throws SQLException;
     List<AddCustomerDTO> getAllCus() throws SQLException;
}
