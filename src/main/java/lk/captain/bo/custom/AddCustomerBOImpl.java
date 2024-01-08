package lk.captain.bo.custom;

import lk.captain.dao.custom.AddCustomerDAO;
import lk.captain.dao.DAOFactory;
import lk.captain.dto.AddCustomerDTO;
import lk.captain.entity.AddCustomer;

import java.sql.SQLException;
import java.util.ArrayList;

public class AddCustomerBOImpl implements AddCustomerBO{
    AddCustomerDAO addCustomerDAO = (AddCustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ADDCUSTOMER);

    @Override
    public boolean addCustomer(AddCustomerDTO addCustomerDTO) throws SQLException, ClassNotFoundException {
        return addCustomerDAO.save(new AddCustomer(addCustomerDTO.getCusId(),addCustomerDTO.getCusName(),addCustomerDTO.getCusTele(),addCustomerDTO.getCusAddress()));
    }

    @Override
    public String generateCusId() throws SQLException, ClassNotFoundException {
        return addCustomerDAO.generateId();
    }

    @Override
    public boolean deleteCus(String cusIds) throws SQLException, ClassNotFoundException {
        return addCustomerDAO.delete(cusIds);
    }

    @Override
    public boolean updateCustomer(AddCustomerDTO dto) throws SQLException, ClassNotFoundException {
        return addCustomerDAO.update(new AddCustomer(dto.getCusId(),dto.getCusName(),dto.getCusTele(),dto.getCusAddress()));
    }

    @Override
    public AddCustomerDTO searchCusId(String id) throws SQLException, ClassNotFoundException {
        AddCustomer addCustomer = addCustomerDAO.search(id);
        AddCustomerDTO addCustomerDTO = new AddCustomerDTO(
                addCustomer.getCusId(),
                addCustomer.getCusName(),
                addCustomer.getCusTele(),
                addCustomer.getCusAddress()
        );
        return addCustomerDTO;
    }

    @Override
    public ArrayList<AddCustomerDTO> getAllCus() throws SQLException, ClassNotFoundException {
        ArrayList<AddCustomer> customers = addCustomerDAO.getAll();
        ArrayList<AddCustomerDTO> customerDTOS = new ArrayList<>();

        for (AddCustomer customer : customers) {
            customerDTOS.add(new AddCustomerDTO(
                   customer.getCusId(),
                    customer.getCusName(),
                    customer.getCusTele(),
                    customer.getCusAddress()
            ));
        }
        return customerDTOS;
    }

}
