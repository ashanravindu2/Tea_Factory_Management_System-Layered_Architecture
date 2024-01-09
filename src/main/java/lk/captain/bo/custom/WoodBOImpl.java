package lk.captain.bo.custom;

import lk.captain.bo.BOFactory;
import lk.captain.dao.DAOFactory;
import lk.captain.dao.custom.WoodDAO;
import lk.captain.dto.WoodMaterialDTO;
import lk.captain.entity.Wood;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WoodBOImpl implements WoodBO{
    WoodDAO woodDAO = (WoodDAO)DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.WOOD);

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return woodDAO.delete(id);
    }

    @Override
    public boolean save(WoodMaterialDTO dto) throws SQLException, ClassNotFoundException {
        return woodDAO.save(new Wood(
                dto.getBarrelId(),
                dto.getWCategory(),
                dto.getWWeight()
        ));
    }

    @Override
    public boolean update(WoodMaterialDTO dto) throws SQLException, ClassNotFoundException {
        return woodDAO.update(new Wood(
                dto.getBarrelId(),
                dto.getWCategory(),
                dto.getWWeight()
        ));
    }

    @Override
    public WoodMaterialDTO search(String id) throws SQLException, ClassNotFoundException {
        Wood wood = woodDAO.search(id);
        WoodMaterialDTO woodMaterialDTO = new WoodMaterialDTO(
                wood.getBarrelId(),
                wood.getWCategory(),
                wood.getWWeight()
        );
        return woodMaterialDTO;
    }

    @Override
    public ResultSet generateId() throws SQLException, ClassNotFoundException {
        return woodDAO.generateId();
    }

    @Override
    public ArrayList<WoodMaterialDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Wood> woods = woodDAO.getAll();
        ArrayList<WoodMaterialDTO> woodDTOS = new ArrayList<>();

        for (Wood wood : woods) {
            woodDTOS.add(new WoodMaterialDTO(
                    wood.getBarrelId(),
                    wood.getWCategory(),
                    wood.getWWeight()
            ));
        }
        return woodDTOS;
    }

    @Override
    public int searchCount() throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public ResultSet getAllAvalable() throws SQLException, ClassNotFoundException {
        return woodDAO.getAllAvalable();
    }

    @Override
    public boolean usedUpdateWood(String id, double used) throws SQLException, ClassNotFoundException {
        return woodDAO.usedUpdateWood(id,used);
    }
}
