package lk.captain.dao.custom;

import lk.captain.dao.CrudDAO;
import lk.captain.db.DbConnection;
import lk.captain.dto.AttendenceDTO;
import lk.captain.entity.Attendence;

import java.sql.*;
import java.time.LocalDate;

public interface AttendenceDAO extends CrudDAO<Attendence> {

     Attendence searchEmplIsDate(String id, String date) throws SQLException, ClassNotFoundException ;
     ResultSet isTodayAtt() throws SQLException, ClassNotFoundException;
     int issearchAttendenceCount(String id, String isdate1, String isdate2) throws SQLException, ClassNotFoundException;

}
