package lk.captain.model;

import lk.captain.Db.DbConnection;
import lk.captain.dto.AttendenceDTO;
import lk.captain.dto.TeaTypeDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AttendenceModel {

    public boolean manage(AttendenceDTO attendenceDTO) throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();

        String isWorker = attendenceDTO.getEmpAttenId();
        boolean is = Pattern.matches("\\b[Ww]\\w*\\b",isWorker);
        String workId = null;
        String teacolId = null;
            if (is){
                workId = isWorker;
            }else {
                teacolId = isWorker;
            }

            String sql = "INSERT INTO empattendence VALUES(?, ?, ?, ?,?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, attendenceDTO.getEmpAttenId());
            pstm.setString(2, attendenceDTO.getName());
            pstm.setInt(3, attendenceDTO.getAttMark());
            pstm.setDate(4, Date.valueOf(attendenceDTO.getDate()));
            pstm.setTime(5, Time.valueOf(attendenceDTO.getTime()));
            pstm.setString(6,workId);
            pstm.setString(7,teacolId);




            return pstm.executeUpdate() > 0;
        }


    public List<AttendenceDTO> getAllAttendeceDetail() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM empattendence";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<AttendenceDTO> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            var dto = new AttendenceDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );

            dtoList.add(dto);
        }

        return dtoList;
    }

    public AttendenceDTO issearchEmpAttendenceId(String id,String isdate) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "\n" +
                "SELECT *\n" +
                "FROM empattendence\n" +
                "WHERE EmpAttenId = ? AND DATE = ? AND attMark = 1;";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);
        pstm.setDate(2, Date.valueOf(isdate));

        ResultSet resultSet = pstm.executeQuery();

        AttendenceDTO dto = null;

        if(resultSet.next()) {
            String EmpAttenId = resultSet.getString(1);
            String name = resultSet.getString(2);
            int attMark = resultSet.getInt(3);
            String date = resultSet.getString(4);
            String time = resultSet.getString(5);


            dto = new AttendenceDTO(EmpAttenId, name, attMark, date,time);
        }

        return dto;


    }
    public boolean isUpdated(String empIds,String isDates) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "\n" +
                "SELECT *\n" +
                "FROM empattendence\n" +
                "WHERE EmpAttenId = ? AND DATE = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, empIds);
        pstm.setDate(2, Date.valueOf(isDates));

        ResultSet resultSet = pstm.executeQuery();

            int temp = 0;
        boolean isnoted = true;
        while (resultSet.next()){
            temp+=1;
            }
        if (temp>=1){
             isnoted=false;
            }
        return isnoted;

    }
    public int issearchAttendenceCount(String id,String isdate1 ,String isdate2) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "select SUM(attMark)='1' AS COUNT FROM empAttendence WHERE EmpAttenId = ? AND DATE(date) BETWEEN  ? AND  ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);
        pstm.setDate(2,  Date.valueOf(String.valueOf(isdate1)));
        pstm.setDate(3, Date.valueOf(String.valueOf(isdate2)));

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            int netTotal = resultSet.getInt("COUNT");
            return netTotal;
        }
        return 0;

    }
    public ResultSet isTodayAtt() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String today = String.valueOf(LocalDate.now());


        String sql = "SELECT\n" +
                "    SUM(CASE WHEN EmpAttenId LIKE 'W%' THEN 1 ELSE 0 END) AS WORKCOUNT,\n" +
                "    SUM(CASE WHEN EmpAttenId LIKE 'K%' THEN 1 ELSE 0 END) AS COLEC\n" +
                "FROM\n" +
                "    empattendence\n" +
                "WHERE\n" +
                "        DATE(date) = ? \n" +
                "  AND attMark = 1;";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setDate(1, Date.valueOf(today));

        ResultSet resultSet = pstm.executeQuery();

        return resultSet;

    }

}
