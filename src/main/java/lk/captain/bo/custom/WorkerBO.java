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

     boolean workerManage(WorkerManageDTO workerManageDto) throws SQLException, ClassNotFoundException ;
     boolean updateWorker(WorkerManageDTO dto) throws SQLException, ClassNotFoundException;
     boolean deleteWorker(String workerId) throws SQLException, ClassNotFoundException;
     ResultSet generateNextWorkerId() throws SQLException, ClassNotFoundException;
     ArrayList<WorkerManageDTO> getAllWorker() throws SQLException, ClassNotFoundException;
     WorkerManageDTO searchWorkerId(String id) throws SQLException, ClassNotFoundException;
     int searchCount() throws SQLException, ClassNotFoundException;
}
