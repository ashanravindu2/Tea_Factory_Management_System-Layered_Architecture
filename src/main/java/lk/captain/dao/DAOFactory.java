package lk.captain.dao;

import lk.captain.dao.custom.impl.*;

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
        TEASUPPLIER,WOOD,TEATYPE,FUEL,WAREHOUSE,USERREG,ATTENDENCE,TEALEAFENTRY,PAYMENT,QUERY,TEASELLS,STOREDETAIL,TEAPOWDER

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
            case FUEL:
                return new FuelDAOImpl();
            case WAREHOUSE:
                return new WareHouseDAOImpl();
            case USERREG:
                return new UserRegDAOImpl();
            case ATTENDENCE:
                return new AttendenceDAOImpl();
            case TEALEAFENTRY:
                return new TeaLeafEntryDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            case TEASELLS:
                return new TeaSellsDAOImpl();
            case STOREDETAIL:
                return new StoreDetailDAOImpl();
            case TEAPOWDER:
                return new TeaPowderDAOImpl();
            default:
                return null;
        }
   }
}
