package lk.captain.dao;

import lk.captain.bo.custom.WorkerBOImpl;
import lk.captain.dao.custom.*;

public class DAOFactory {
    //singleton design pattern
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory(){
        return (daoFactory==null)?daoFactory=new DAOFactory():(daoFactory);
    }
    public enum DAOTypes{
        ADDCUSTOMER,
        TEACOLLECTOR,
        WORKER,
        TEASUPPLIER,WOOD,TEATYPE

    }

   public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case ADDCUSTOMER:
                return new AddCustomerDAOImpl();
                case TEACOLLECTOR:
                return new TeaCollectorDAOImpl();
            case WORKER:
                return new WorkerDAOImpl();
                case TEASUPPLIER:
                return new TeaSupplierDAOImpl();
            case WOOD:
                return new WoodDAOImpl();
            case TEATYPE:
                return new TeaTypeDAOImpl();
            default:
                return null;
        }
   }
}
