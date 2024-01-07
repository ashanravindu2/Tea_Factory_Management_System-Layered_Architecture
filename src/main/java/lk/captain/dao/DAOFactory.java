package lk.captain.dao;

public class DAOFactory {
    //singleton design pattern
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory(){
        return (daoFactory==null)?daoFactory=new DAOFactory():(daoFactory);
    }
    public enum DAOTypes{
        ADDCUSTOMER
    }

   public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case ADDCUSTOMER:
                return new AddCustomerDAOImpl();
            default:
                return null;
        }
   }
}
