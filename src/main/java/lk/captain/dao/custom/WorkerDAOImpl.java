package lk.captain.dao.custom;

import lk.captain.dao.SQLUtil;
import lk.captain.db.DbConnection;
import lk.captain.dto.WorkerManageDTO;
import lk.captain.entity.Worker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkerDAOImpl implements WorkerDAO {

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "DELETE FROM workers WHERE workId = ?", id);
    }

    @Override
    public boolean save(Worker dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO workers(workId,workName,workAdress,workAge,workTele,workGen,workJoin,workBirth) VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                dto.getWorkId(),
                dto.getWorkName(),
                dto.getWorkAdress(),
                dto.getWorkAge(),
                dto.getWorkTele(),
                dto.getWorkGen(),
                dto.getWorkJoin(),
                dto.getWorkBirth()
        );
    }

    @Override
    public boolean update(Worker dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE workers SET workName = ?, workAdress = ? ,workAge =? ,workTele=?,workGen=?,workJoin=?,workBirth=? WHERE workId= ?",
                dto.getWorkName(),
                dto.getWorkAdress(),
                dto.getWorkAge(),
                dto.getWorkTele(),
                dto.getWorkGen(),
                dto.getWorkJoin(),
                dto.getWorkBirth(),
                dto.getWorkId()
        );

    }

    @Override
    public Worker search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM workers WHERE workId = ?", id);

        WorkerManageDTO dto = null;

        if(resultSet.next()) {
            return new Worker(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8)
            );
        }
        return null;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        try {
            ResultSet resultSet =SQLUtil.execute("SELECT workId FROM workers ORDER BY workId DESC LIMIT 1");
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

    @Override
    public ArrayList<Worker> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Worker> dtoList = new ArrayList<>();

        ResultSet resultSet =  SQLUtil.execute("SELECT * FROM workers");
        while (resultSet.next()) {
            Worker worker = new Worker(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8)
            );
            dtoList.add(worker);
        }
        return dtoList;
    }

    public int searchCount() throws SQLException, ClassNotFoundException {

        int count =0;
        ResultSet result = SQLUtil.execute("SELECT COUNT(workId) FROM workers");
        if (result.next()){
            count=result.getInt(1);
        }

        return count;
    }
}
