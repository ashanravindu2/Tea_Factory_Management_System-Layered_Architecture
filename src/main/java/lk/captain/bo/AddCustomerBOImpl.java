package lk.captain.bo;

import lk.captain.dao.AddCustomerDAO;
import lk.captain.dao.DAOFactory;
import lk.captain.dto.AddCustomerDTO;

import java.sql.SQLException;
import java.util.List;

public class AddCustomerBOImpl implements AddCustomerBO{
    AddCustomerDAO addCustomerDAO = (AddCustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ADDCUSTOMER);

    @Override
    public boolean addCustomer(AddCustomerDTO addCustomerDTO) throws SQLException {
        return false;
    }

    @Override
    public String generateCusId() throws SQLException {
        return null;
    }

    @Override
    public boolean deleteCus(String cusIds) throws SQLException, ClassNotFoundException {
        return addCustomerDAO.delete(cusIds);
    }

    @Override
    public boolean updateCustomer(AddCustomerDTO dto) throws SQLException {
        return false;
    }

    @Override
    public AddCustomerDTO searchCusId(String id) throws SQLException {
        return null;
    }

    @Override
    public List<AddCustomerDTO> getAllCus() throws SQLException {
        return null;
    }
}
