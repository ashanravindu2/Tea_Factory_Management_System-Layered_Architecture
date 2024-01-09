package lk.captain.bo.custom;

import lk.captain.dao.DAOFactory;
import lk.captain.dao.custom.TeaCollectorDAO;
import lk.captain.dao.custom.TeaSupplierDAO;
import lk.captain.dto.SupplierManageDTO;
import lk.captain.entity.TeaSupplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeaSupplierBOImpl implements TeaSupplierBO{
    TeaSupplierDAO teaSupplierDAO = (TeaSupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TEASUPPLIER);

    @Override
    public boolean supplierSave(SupplierManageDTO supplierManageDTO) throws SQLException, ClassNotFoundException {
        return teaSupplierDAO.save(new TeaSupplier(
                supplierManageDTO.getSupplierId(),
                supplierManageDTO.getSuppName(),
                supplierManageDTO.getSuppAddres(),
                supplierManageDTO.getSuppTele(),
                supplierManageDTO.getSuppGen()));
    }

    @Override
    public ResultSet generateSupId() throws SQLException, ClassNotFoundException {
        return teaSupplierDAO.generateId();
    }

    @Override
    public List<SupplierManageDTO> getAllTeaSupp() throws SQLException, ClassNotFoundException {
        ArrayList<TeaSupplier> teaSuppliers = teaSupplierDAO.getAll();
        ArrayList<SupplierManageDTO> supplierManageDTOS = new ArrayList<>();

        for (TeaSupplier teaSupplier : teaSuppliers) {
            supplierManageDTOS.add(new SupplierManageDTO(
                    teaSupplier.getSupplierId(),
                    teaSupplier.getSuppName(),
                    teaSupplier.getSuppAddres(),
                    teaSupplier.getSuppTele(),
                    teaSupplier.getSuppGen()
            ));
        }
        return supplierManageDTOS;
    }

    @Override
    public boolean deleteSupplier(String suppId) throws SQLException, ClassNotFoundException {
        return teaSupplierDAO.delete(suppId);
    }

    @Override
    public boolean updateSupplier(SupplierManageDTO dto) throws SQLException, ClassNotFoundException {
        return teaSupplierDAO.update(
                new TeaSupplier(
                        dto.getSupplierId(),
                        dto.getSuppName(),
                        dto.getSuppAddres(),
                        dto.getSuppTele(),
                        dto.getSuppGen()
                )
        );
    }

    @Override
    public SupplierManageDTO searchSupplierId(String id) throws SQLException, ClassNotFoundException {
        TeaSupplier teaSupplier = teaSupplierDAO.search(id);
        SupplierManageDTO supplierManageDTO = new SupplierManageDTO(
                teaSupplier.getSupplierId(),
                teaSupplier.getSuppName(),
                teaSupplier.getSuppAddres(),
                teaSupplier.getSuppTele(),
                teaSupplier.getSuppGen()
        );
        return supplierManageDTO;
    }
}
