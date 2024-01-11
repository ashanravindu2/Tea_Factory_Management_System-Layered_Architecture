package lk.captain.bo.custom.impl;

import lk.captain.bo.custom.FuelBO;
import lk.captain.dao.DAOFactory;
import lk.captain.dao.SQLUtil;
import lk.captain.dao.custom.FuelDAO;
import lk.captain.dao.custom.TeaCollectorDAO;
import lk.captain.dto.FuelMaterialDTO;
import lk.captain.entity.Fuel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuelBOImpl implements FuelBO {

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
    public boolean update(FuelMaterialDTO fuelMaterialDTO) throws SQLException, ClassNotFoundException {
        return fuelDAO.update(new Fuel(
                fuelMaterialDTO.getBarrelId(),
                fuelMaterialDTO.getBCategory(),
                fuelMaterialDTO.getBLeter()
        ));
    }

    @Override
    public List<FuelMaterialDTO> getAllFuel() throws SQLException, ClassNotFoundException {
        ArrayList<Fuel> fuels = fuelDAO.getAll();
        ArrayList<FuelMaterialDTO> fuelMaterialDTOS = new ArrayList<>();

        for (Fuel fuel : fuels) {
            fuelMaterialDTOS.add(new FuelMaterialDTO(
                    fuel.getBarrelId(),
                    fuel.getBCategory(),
                    fuel.getBLeter()
            ));
        }
        return fuelMaterialDTOS;
    }

    @Override
    public ResultSet generateFuelId() throws SQLException, ClassNotFoundException {
        return fuelDAO.generateId();
    }

    @Override
    public boolean usedUpdateFuel(String id, double liter) throws SQLException, ClassNotFoundException {
        return fuelDAO.usedUpdateFuel(id,liter);
    }

    @Override
    public ResultSet getAllAvalable() throws SQLException, ClassNotFoundException {
        return fuelDAO.getAllAvalable();
    }
}
