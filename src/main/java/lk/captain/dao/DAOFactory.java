package lk.captain.dao;

import lk.captain.dao.custom.AddCustomerDAOImpl;
import lk.captain.dao.custom.TeaCollectorDAOImpl;

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
        TEACOLLECTOR
    }

   public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case ADDCUSTOMER:
                return new AddCustomerDAOImpl();
                case TEACOLLECTOR:
                return new TeaCollectorDAOImpl();
            default:
                return null;
        }
   }
}
