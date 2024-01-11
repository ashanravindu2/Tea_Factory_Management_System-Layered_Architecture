package lk.captain.bo.custom;

import lk.captain.bo.SuperBO;
import lk.captain.dao.SQLUtil;
import lk.captain.db.DbConnection;
import lk.captain.dto.AttendenceDTO;
import lk.captain.entity.Attendence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public interface AttendenceBO extends SuperBO {

     boolean manage(AttendenceDTO attendenceDTO) throws SQLException, ClassNotFoundException;
     ArrayList<AttendenceDTO> getAllAttendeceDetail() throws SQLException, ClassNotFoundException;
     boolean update(AttendenceDTO dto) throws SQLException, ClassNotFoundException;
     int issearchAttendenceCount(String id,String isdate1 ,String isdate2) throws SQLException, ClassNotFoundException;
     AttendenceDTO searchEmplIsDate(String id, String date) throws SQLException, ClassNotFoundException;
     ResultSet isTodayAtt() throws SQLException, ClassNotFoundException;
}
