package lk.captain.bo.custom.impl;

import lk.captain.bo.custom.TeaLeafEntryBO;
import lk.captain.dao.DAOFactory;
import lk.captain.dao.custom.TeaLeafEntryDAO;
import lk.captain.dto.AddCustomerDTO;
import lk.captain.dto.TeaLeafEntryDTO;
import lk.captain.entity.AddCustomer;
import lk.captain.entity.TeaLeafEntry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeaLeafEntryBOImpl implements TeaLeafEntryBO {
    TeaLeafEntryDAO teaLeafDAO = (TeaLeafEntryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TEALEAFENTRY);

    @Override
    public boolean updateTeaLeaf(TeaLeafEntryDTO dto) throws SQLException, ClassNotFoundException {
        return teaLeafDAO.update(new TeaLeafEntry(
                dto.getSupplierId(),
                dto.getTeaColecId(),
                dto.getGrosWeight(),
                dto.getWaterCon(),
                dto.getNetWeight(),
                dto.getDate()));
    }

    @Override
    public boolean tealeafManage(TeaLeafEntryDTO teaLeafEntryDTO) throws SQLException, ClassNotFoundException {
        return teaLeafDAO.save(new TeaLeafEntry(
                teaLeafEntryDTO.getSupplierId(),
                teaLeafEntryDTO.getTeaColecId(),
                teaLeafEntryDTO.getGrosWeight(),
                teaLeafEntryDTO.getWaterCon(),
                teaLeafEntryDTO.getNetWeight(),
                teaLeafEntryDTO.getDate()));
    }

    @Override
    public ArrayList<TeaLeafEntryDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<TeaLeafEntry> teaLeafEntries = teaLeafDAO.getAll();
        ArrayList<TeaLeafEntryDTO> teaLeafEntryDTOS = new ArrayList<>();

        for (TeaLeafEntry teaLeafEntry : teaLeafEntries) {
            teaLeafEntryDTOS.add(new TeaLeafEntryDTO(
                    teaLeafEntry.getSupplierId(),
                    teaLeafEntry.getTeaColecId(),
                    teaLeafEntry.getGrosWeight(),
                    teaLeafEntry.getWaterCon(),
                    teaLeafEntry.getNetWeight(),
                    teaLeafEntry.getDate()
            ));
        }
        return teaLeafEntryDTOS;
    }

    @Override
    public double getNetTotal(String date) throws SQLException, ClassNotFoundException {
        return teaLeafDAO.getNetTotal(date);
    }

    @Override
    public TeaLeafEntryDTO seacrhTeaLeaf(String id, String date) throws SQLException, ClassNotFoundException {
        TeaLeafEntry teaLeafEntry = teaLeafDAO.seacrhTeaLeaf(id, date);
        TeaLeafEntryDTO teaLeafEntryDTO = new TeaLeafEntryDTO(
                teaLeafEntry.getSupplierId(),
                teaLeafEntry.getTeaColecId(),
                teaLeafEntry.getGrosWeight(),
                teaLeafEntry.getWaterCon(),
                teaLeafEntry.getNetWeight(),
                teaLeafEntry.getDate()
        );
        return teaLeafEntryDTO;
    }

    @Override
    public boolean deleteTeaLeaf(String suppId, String date) throws SQLException, ClassNotFoundException {
        return teaLeafDAO.deleteTeaLeaf(suppId,date);
    }

    @Override
    public double getReducedTotal(String date) throws SQLException, ClassNotFoundException {
        return teaLeafDAO.getReducedTotal(date);
    }

    @Override
    public double getNetValue(String date1, String date2, String suppId) throws SQLException, ClassNotFoundException {
        return teaLeafDAO.getNetValue(date1,date2,suppId);
    }
}
