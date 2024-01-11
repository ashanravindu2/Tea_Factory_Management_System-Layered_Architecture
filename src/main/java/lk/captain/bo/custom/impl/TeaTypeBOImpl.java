package lk.captain.bo.custom.impl;

import lk.captain.bo.BOFactory;
import lk.captain.bo.custom.TeaTypeBO;
import lk.captain.dao.DAOFactory;
import lk.captain.dao.custom.TeaTypeDAO;
import lk.captain.dao.custom.WoodDAO;
import lk.captain.dto.TeaTypeDTO;
import lk.captain.entity.TeaType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeaTypeBOImpl implements TeaTypeBO {
    TeaTypeDAO teaTypeDAO = (TeaTypeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TEATYPE);
    @Override
    public boolean teatypeSave(TeaTypeDTO teaTypeDTO) throws SQLException, ClassNotFoundException {
        return teaTypeDAO.save(new TeaType(
                teaTypeDTO.getTeaTypeId(),
                teaTypeDTO.getTeaTypeName(),
                teaTypeDTO.getTeaTypeDesc(),
                teaTypeDTO.getTeaPerPrice()
        ));
    }

    @Override
    public ArrayList<TeaTypeDTO> loadAllTeaTypes() throws SQLException, ClassNotFoundException {
        ArrayList<TeaTypeDTO> teaTypeDTOS = new ArrayList<>();
        ArrayList<TeaType> allTeaTypes = teaTypeDAO.getAll();
        for (TeaType teaType : allTeaTypes) {
            teaTypeDTOS.add(new TeaTypeDTO(
                    teaType.getTeaTypeId(),
                    teaType.getTeaTypeName(),
                    teaType.getTeaTypeDesc(),
                    teaType.getTeaPerPrice()
            ));
        }
        return teaTypeDTOS;
    }

    @Override
    public boolean deleteTeaType(String teaTypeId) throws SQLException, ClassNotFoundException {
        return teaTypeDAO.delete(teaTypeId);
    }

    @Override
    public TeaTypeDTO serchOnTeaType(String id) throws SQLException, ClassNotFoundException {
        TeaType teaType = teaTypeDAO.search(id);
        TeaTypeDTO teaTypeDTO = new TeaTypeDTO(
                teaType.getTeaTypeId(),
                teaType.getTeaTypeName(),
                teaType.getTeaTypeDesc(),
                teaType.getTeaPerPrice()
        );
        return teaTypeDTO;
    }

    @Override
    public boolean updateTeaType(TeaTypeDTO dto) throws SQLException, ClassNotFoundException {
        return teaTypeDAO.update(new TeaType(
                dto.getTeaTypeId(),
                dto.getTeaTypeName(),
                dto.getTeaTypeDesc(),
                dto.getTeaPerPrice()
        ));
    }
}
