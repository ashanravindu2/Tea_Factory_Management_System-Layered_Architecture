package lk.captain.bo.custom;

import lk.captain.dao.DAOFactory;
import lk.captain.dao.SQLUtil;
import lk.captain.dao.custom.AddCustomerDAO;
import lk.captain.dao.custom.TeaCollectorDAO;
import lk.captain.db.DbConnection;
import lk.captain.dto.AddCustomerDTO;
import lk.captain.dto.TeaCollctorDTO;
import lk.captain.entity.AddCustomer;
import lk.captain.entity.TeaCollector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeaCollectorBOImpl implements TeaCollectorBO{
    TeaCollectorDAO teaCollectorDAO = (TeaCollectorDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TEACOLLECTOR);

    @Override
    public boolean save(TeaCollctorDTO dto) throws SQLException, ClassNotFoundException {
        return teaCollectorDAO.save(new TeaCollector(dto.getTeaColecId(),dto.getName(),dto.getAddress(),dto.getTelephone(),dto.getGender()));
    }

    @Override
    public ResultSet generateColecId() throws SQLException, ClassNotFoundException {
        return teaCollectorDAO.generateId();
    }

    @Override
    public ArrayList<TeaCollctorDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<TeaCollector> teaCollectors = teaCollectorDAO.getAll();
        ArrayList<TeaCollctorDTO> teaCollctorDTOS = new ArrayList<>();

        for (TeaCollector teaCollctorDTO : teaCollectors) {
            teaCollctorDTOS.add(new TeaCollctorDTO(
                    teaCollctorDTO.getTeaColecId(),
                    teaCollctorDTO.getName(),
                    teaCollctorDTO.getAddress(),
                    teaCollctorDTO.getTelephone(),
                    teaCollctorDTO.getGender()
            ));
        }
      return teaCollctorDTOS;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return teaCollectorDAO.delete(id);
    }

    @Override
    public boolean update(TeaCollctorDTO dto) throws SQLException, ClassNotFoundException {
        return teaCollectorDAO.update(new TeaCollector(dto.getTeaColecId(),dto.getName(),dto.getAddress(),dto.getTelephone(),dto.getGender()));
    }
    public TeaCollctorDTO searchTeaColecId(String id) throws SQLException, ClassNotFoundException {
        TeaCollector teaCollector = teaCollectorDAO.search(id);
        TeaCollctorDTO teaCollctorDTO = new TeaCollctorDTO(
                teaCollector.getTeaColecId(),
                teaCollector.getName(),
                teaCollector.getAddress(),
                teaCollector.getTelephone(),
                teaCollector.getGender()
        );
        return teaCollctorDTO;
    }
    public int searchCount() throws SQLException, ClassNotFoundException {
        return teaCollectorDAO.searchCount();
    }
}
