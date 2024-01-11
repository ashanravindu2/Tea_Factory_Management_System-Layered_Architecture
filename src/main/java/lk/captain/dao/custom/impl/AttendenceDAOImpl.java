package lk.captain.dao.custom.impl;

import lk.captain.dao.SQLUtil;
import lk.captain.dao.custom.AttendenceDAO;
import lk.captain.db.DbConnection;
import lk.captain.dto.AttendenceDTO;
import lk.captain.entity.Attendence;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class AttendenceDAOImpl implements AttendenceDAO {
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean save(Attendence dto) throws SQLException, ClassNotFoundException {
        String isWorker = dto.getEmpAttenId();
        boolean is = Pattern.matches("\\b[Ww]\\w*\\b", isWorker);
        String workId = null;
        String teacolId = null;
        if (is) {
            workId = isWorker;
        } else {
            teacolId = isWorker;
        }
        return SQLUtil.execute("INSERT INTO empattendence VALUES(?, ?, ?, ?,?,?,?)",
                dto.getEmpAttenId(), dto.getName(), dto.getAttMark(), dto.getDate(), dto.getTime(), workId, teacolId);
    }

    @Override
    public boolean update(Attendence dto) throws SQLException, ClassNotFoundException {
            ResultSet resultSet = SQLUtil.execute(" SELECT * FROM empattendence WHERE EmpAttenId = ? AND DATE = ?",dto.getEmpAttenId(),dto.getDate());
            int temp = 0;
            boolean isnoted = true;
            while (resultSet.next()) {
                temp += 1;
            }
            if (temp >= 1) {
                isnoted = false;
            }
            return isnoted;
    }


    @Override
    public Attendence search(String id) throws SQLException, ClassNotFoundException {
       return null;
    }

    @Override
    public ResultSet generateId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Attendence> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Attendence> dtoList = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM empattendence");
        while (resultSet.next()) {
            Attendence attendence = new Attendence(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            dtoList.add(attendence);
        }
        return dtoList;
    }

    @Override
    public int searchCount() throws SQLException, ClassNotFoundException {
        return 0;
    }

    public int issearchAttendenceCount(String id, String isdate1, String isdate2) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getDbConnection().getConnection();

        String sql = "select SUM(attMark)='1' AS COUNT FROM empAttendence WHERE EmpAttenId = ? AND DATE(date) BETWEEN  ? AND  ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);
        pstm.setDate(2, Date.valueOf(String.valueOf(isdate1)));
        pstm.setDate(3, Date.valueOf(String.valueOf(isdate2)));

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            int netTotal = resultSet.getInt("COUNT");
            return netTotal;
        }
        return 0;
    }

    @Override
    public Attendence searchEmplIsDate(String id,String date) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("SELECT *\n" +
                "FROM empattendence\n" +
                "WHERE EmpAttenId = ? AND DATE = ? AND attMark = 1;", id, date);

    }

    @Override
    public ResultSet isTodayAtt() throws SQLException, ClassNotFoundException {
            String today = String.valueOf(LocalDate.now());
       return SQLUtil.execute( "SELECT\n" +
                    "    SUM(CASE WHEN EmpAttenId LIKE 'W%' THEN 1 ELSE 0 END) AS WORKCOUNT,\n" +
                    "    SUM(CASE WHEN EmpAttenId LIKE 'K%' THEN 1 ELSE 0 END) AS COLEC\n" +
                    "FROM\n" +
                    "    empattendence\n" +
                    "WHERE\n" +
                    "        DATE(date) = ? \n" +
                    "  AND attMark = 1;",today);

    }
}