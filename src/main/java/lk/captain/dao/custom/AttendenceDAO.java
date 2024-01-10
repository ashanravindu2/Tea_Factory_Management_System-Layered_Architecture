package lk.captain.dao.custom;

import lk.captain.dao.CrudDAO;
import lk.captain.db.DbConnection;
import lk.captain.dto.AttendenceDTO;
import lk.captain.entity.Attendence;

import java.sql.*;
import java.time.LocalDate;

public interface AttendenceDAO extends CrudDAO<Attendence> {
    public Attendence searchEmplIsDate(String id, String date) throws SQLException, ClassNotFoundException ;
    public ResultSet isTodayAtt() throws SQLException, ClassNotFoundException;
    public int issearchAttendenceCount(String id, String isdate1, String isdate2) throws SQLException, ClassNotFoundException;

}
