package lk.captain.bo.custom;

import lk.captain.dao.DAOFactory;
import lk.captain.dao.SQLUtil;
import lk.captain.dao.custom.FuelDAO;
import lk.captain.dao.custom.TeaCollectorDAO;
import lk.captain.dto.FuelMaterialDTO;
import lk.captain.entity.Fuel;

import java.sql.SQLException;

public class FuelBOImpl implements FuelBO{

    FuelDAO fuelDAO = (FuelDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.FUEL);
    @Override
    public boolean fuelSave(FuelMaterialDTO fuelMaterialDTO) throws SQLException, ClassNotFoundException {
        return fuelDAO.save(new Fuel(
                fuelMaterialDTO.getBarrelId(),
                fuelMaterialDTO.getBCategory(),
                fuelMaterialDTO.getBLeter()
        ));
    }

    @Override
    public boolean deleteFuel(String id) throws SQLException, ClassNotFoundException {
       return fuelDAO.delete(id);
    }

    @Override
    public FuelMaterialDTO searchFuelId(String id) throws SQLException, ClassNotFoundException {
        Fuel fuel = fuelDAO.search(id);
        return new FuelMaterialDTO(
                fuel.getBarrelId(),
                fuel.getBCategory(),
                fuel.getBLeter()
        );
    }

    @Override
    public boolean usedUpdateFuel(FuelMaterialDTO fuelMaterialDTO) throws SQLException {
        return false;
    }
}
