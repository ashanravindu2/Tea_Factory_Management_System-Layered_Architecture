package lk.captain.bo.custom.impl;

import lk.captain.bo.custom.AttendenceBO;
import lk.captain.dao.DAOFactory;
import lk.captain.dao.custom.AddCustomerDAO;
import lk.captain.dao.custom.AttendenceDAO;
import lk.captain.dto.AttendenceDTO;
import lk.captain.entity.AddCustomer;
import lk.captain.entity.Attendence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendenceBOImpl implements AttendenceBO {

    AttendenceDAO attendenceDAO = (AttendenceDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ATTENDENCE);
    @Override
    public boolean manage(AttendenceDTO attendenceDTO) throws SQLException, ClassNotFoundException {
        return attendenceDAO.save(new Attendence(attendenceDTO.getEmpAttenId(),attendenceDTO.getName(),attendenceDTO.getAttMark(),attendenceDTO.getDate(),attendenceDTO.getTime()));
    }

    @Override
    public ArrayList<AttendenceDTO> getAllAttendeceDetail() throws SQLException, ClassNotFoundException {
        ArrayList<Attendence> attendences = attendenceDAO.getAll();
         ArrayList<AttendenceDTO> attendenceDTOS = new ArrayList<>();

for (Attendence attendence : attendences) {
             attendenceDTOS.add(new AttendenceDTO(
                     attendence.getEmpAttenId(),
                     attendence.getName(),
                     attendence.getAttMark(),
                     attendence.getDate(),
                     attendence.getTime()
             ));
         }
            return attendenceDTOS;
    }

    @Override
    public boolean update(AttendenceDTO dto) throws SQLException, ClassNotFoundException {
        return attendenceDAO.update(new Attendence(dto
                .getEmpAttenId(),dto.getName(),dto.getAttMark(),dto.getDate(),dto.getTime()));
    }


    @Override
    public int issearchAttendenceCount(String id, String isdate1, String isdate2) throws SQLException, ClassNotFoundException {
        return attendenceDAO.issearchAttendenceCount(id,isdate1,isdate2);
    }

    @Override
    public AttendenceDTO searchEmplIsDate(String id, String date) throws SQLException, ClassNotFoundException {
        Attendence attendence = attendenceDAO.searchEmplIsDate(id,date);
        AttendenceDTO attendenceDTO = new AttendenceDTO(
                attendence.getEmpAttenId(),
                attendence.getName(),
                attendence.getAttMark(),
                attendence.getDate(),
                attendence.getTime()
        );
        return attendenceDTO;

    }

    @Override
    public ResultSet isTodayAtt() throws SQLException, ClassNotFoundException {
        return attendenceDAO.isTodayAtt();
    }
}


