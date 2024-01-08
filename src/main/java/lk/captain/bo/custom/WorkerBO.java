package lk.captain.bo.custom;

import lk.captain.bo.SuperBO;
import lk.captain.dao.SQLUtil;
import lk.captain.dto.WorkerManageDTO;
import lk.captain.entity.Worker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface WorkerBO extends SuperBO {
    public boolean workerManage(WorkerManageDTO workerManageDto) throws SQLException, ClassNotFoundException ;

    public boolean updateWorker(WorkerManageDTO dto) throws SQLException, ClassNotFoundException;

    public boolean deleteWorker(String workerId) throws SQLException, ClassNotFoundException;

    public String generateNextWorkerId() throws SQLException, ClassNotFoundException;

    public ArrayList<WorkerManageDTO> getAllWorker() throws SQLException, ClassNotFoundException;

        public WorkerManageDTO searchWorkerId(String id) throws SQLException, ClassNotFoundException;

    public int searchCount() throws SQLException, ClassNotFoundException;
}
