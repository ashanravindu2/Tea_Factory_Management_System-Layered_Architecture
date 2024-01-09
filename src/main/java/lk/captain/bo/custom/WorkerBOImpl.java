package lk.captain.bo.custom;

import lk.captain.dao.DAOFactory;
import lk.captain.dao.custom.TeaCollectorDAO;
import lk.captain.dao.custom.WorkerDAO;
import lk.captain.dto.WorkerManageDTO;
import lk.captain.entity.Worker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkerBOImpl implements WorkerBO{

    WorkerDAO workerDAO = (WorkerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.WORKER);


    @Override
    public boolean workerManage(WorkerManageDTO workerManageDto) throws SQLException, ClassNotFoundException {
        return workerDAO.save(new Worker(
                        workerManageDto.getWorkId(),
                        workerManageDto.getWorkName(),
                        workerManageDto.getWorkAdress(),
                        workerManageDto.getWorkAge(),
                        workerManageDto.getWorkTele(),
                        workerManageDto.getWorkGen(),
                        workerManageDto.getWorkJoin(),
                        workerManageDto.getWorkBirth()
        ));
    }

    @Override
    public boolean updateWorker(WorkerManageDTO dto) throws SQLException, ClassNotFoundException {
        return workerDAO.update(new Worker(
                dto.getWorkId(),
                dto.getWorkName(),
                dto.getWorkAdress(),
                dto.getWorkAge(),
                dto.getWorkTele(),
                dto.getWorkGen(),
                dto.getWorkJoin(),
                dto.getWorkBirth()
        )   );
    }

    @Override
    public boolean deleteWorker(String workerId) throws SQLException, ClassNotFoundException {
        return workerDAO.delete(workerId);
    }

    @Override
    public ResultSet generateNextWorkerId() throws SQLException, ClassNotFoundException {
        return workerDAO.generateId();
    }

    @Override
    public ArrayList<WorkerManageDTO> getAllWorker() throws SQLException, ClassNotFoundException {
        ArrayList<Worker> workers = workerDAO.getAll();
        ArrayList<WorkerManageDTO> workerManageDTOS = new ArrayList<>();

        for (Worker worker : workers) {
            workerManageDTOS.add(new WorkerManageDTO(
                    worker.getWorkId(),
                    worker.getWorkName(),
                    worker.getWorkAdress(),
                    worker.getWorkAge(),
                    worker.getWorkTele(),
                    worker.getWorkGen(),
                    worker.getWorkJoin(),
                    worker.getWorkBirth()
            ));
        }
        return workerManageDTOS;
    }

    @Override
    public WorkerManageDTO searchWorkerId(String id) throws SQLException, ClassNotFoundException {
        Worker worker = workerDAO.search(id);
        WorkerManageDTO workerManageDTO = new WorkerManageDTO(
                worker.getWorkId(),
                worker.getWorkName(),
                worker.getWorkAdress(),
                worker.getWorkAge(),
                worker.getWorkTele(),
                worker.getWorkGen(),
                worker.getWorkJoin(),
                worker.getWorkBirth()
        );
        return workerManageDTO;
    }

    @Override
    public int searchCount() throws SQLException, ClassNotFoundException {
        return workerDAO.searchCount();
    }
}
