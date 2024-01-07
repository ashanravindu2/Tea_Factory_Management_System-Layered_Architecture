package lk.captain.model;

import lk.captain.db.DbConnection;
import lk.captain.dto.WorkerManageDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WokerManageModel {


    public static boolean workerManage(WorkerManageDTO workerManageDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO workers(workId,workName,workAdress,workAge,workTele,workGen,workJoin,workBirth) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,workerManageDto.getWorkId() );
        pstm.setString(2,workerManageDto.getWorkName() );
        pstm.setString(3,workerManageDto.getWorkAdress());
        pstm.setInt(4,workerManageDto.getWorkAge());
        pstm.setString(5,workerManageDto.getWorkTele());
        pstm.setString(6,workerManageDto.getWorkGen());
        pstm.setDate(7, Date.valueOf(String.valueOf(workerManageDto.getWorkJoin())));
        pstm.setDate(8, Date.valueOf(String.valueOf(workerManageDto.getWorkBirth())));
      //  preparedStatement.setDate(1, sqlWorkJoinDate);

        return pstm.executeUpdate() > 0;
    }
    public String generateNextWorkerId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT workId FROM workers ORDER BY workId DESC LIMIT 1");
            boolean isExist = resultSet.next();

            if (isExist) {
                String currentWorkerId = resultSet.getString(1);
                currentWorkerId = currentWorkerId.substring(1, currentWorkerId.length());
                int intId = Integer.parseInt(currentWorkerId);
                intId = intId + 1;

                if (intId < 10) {
                    return "W00" + intId;
                } else if (intId < 100) {
                    return "W0" + intId;
                } else {
                    return "W" + intId;
                }

            } else {
                return "W001";
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<WorkerManageDTO> getAllWorker() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM workers";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<WorkerManageDTO> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String workId = resultSet.getString(1);
            String workName = resultSet.getString(2);
            String workAdress = resultSet.getString(3);
            int workAge = resultSet.getInt(4);
            String workTele =resultSet.getString(5);
            String workGen =resultSet.getString(6);
            String workJoin =resultSet.getString(7);
            String workBirth =resultSet.getString(8);

            var dto = new WorkerManageDTO(workId, workName, workAdress, workAge,workTele,workGen,workJoin,workBirth);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public WorkerManageDTO searchWorkerId(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM workers WHERE workId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        WorkerManageDTO dto = null;

        if(resultSet.next()) {
            String workId = resultSet.getString(1);
            String workName = resultSet.getString(2);
            String workAdd = resultSet.getString(3);
            int workAge = resultSet.getInt(4);
            String worktele = resultSet.getString(5);
            String workgen = resultSet.getString(6);
            String workJoin = resultSet.getString(7);
            String workBirth =resultSet.getString(8);

            dto = new WorkerManageDTO(workId, workName, workAdd, workAge,worktele,workgen,workJoin,workBirth);
        }

        return dto;
    }

    public boolean updateWorker(WorkerManageDTO dto) throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE workers SET workName = ?, workAdress = ? ,workAge =? ,workTele=?,workGen=?,workJoin=?,workBirth=? WHERE workId= ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getWorkName());
        pstm.setString(2, dto.getWorkAdress());
        pstm.setDouble(3, dto.getWorkAge());
        pstm.setString(4, dto.getWorkTele());
        pstm.setString(5,dto.getWorkGen());
        pstm.setString(6, dto.getWorkJoin());
        pstm.setString(7,dto.getWorkBirth());
        pstm.setString(8,dto.getWorkId());

        return pstm.executeUpdate() > 0;
    }

    public boolean deleteWorker(String workerId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM workers WHERE workId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, workerId);

        return pstm.executeUpdate() > 0;
    }
    public int searchCount() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(workId) FROM workers";
        PreparedStatement pstm = connection.prepareStatement(sql);

        int count =0;
        ResultSet result = pstm.executeQuery();
        if (result.next()){
            count=result.getInt(1);
        }

        return count;
    }
}
