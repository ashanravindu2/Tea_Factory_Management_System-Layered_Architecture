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

    public boolean manage(AttendenceDTO attendenceDTO) throws SQLException, ClassNotFoundException;

    public ArrayList<AttendenceDTO> getAllAttendeceDetail() throws SQLException, ClassNotFoundException;

    public boolean update(AttendenceDTO dto) throws SQLException, ClassNotFoundException;

    public int issearchAttendenceCount(String id,String isdate1 ,String isdate2) throws SQLException, ClassNotFoundException;

    public AttendenceDTO searchEmplIsDate(String id, String date) throws SQLException, ClassNotFoundException;

    public ResultSet isTodayAtt() throws SQLException, ClassNotFoundException;
}
