package lk.captain.bo;

import lk.captain.bo.custom.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBoFactory(){
        return (boFactory==null)?boFactory=new BOFactory():(boFactory);
    }

    public enum BOTypes {
        ADDCUSTOMER
        ,TEACOLLECTOR
        ,WORKER,TEASUPPLIER,WOOD,TEATYPE,FUEL,WAREHOUSE,USERREG,ATTENDENCE,TEALEAFENTRY,PAYMENT,QUERY,TEASELLS,STOREDETAIL,
        TEAPOWDER
    }
    public SuperBO getBOTypes(BOTypes boTypes) {
        switch (boTypes) {
            case ADDCUSTOMER:
                return new AddCustomerBOImpl();
                case TEACOLLECTOR:
                return new TeaCollectorBOImpl();
                case WORKER:
                    return new WorkerBOImpl();
                    case TEASUPPLIER:
                    return new TeaSupplierBOImpl();
                    case WOOD:
                    return new WoodBOImpl();
                    case TEATYPE:
                    return new TeaTypeBOImpl();
                    case FUEL:
                    return new FuelBOImpl();
                    case WAREHOUSE:
                    return new WareHouseBOImpl();
                    case USERREG:
                    return new UserRegBOImpl();
                    case ATTENDENCE:
                    return new AttendenceBOImpl();
                    case TEALEAFENTRY:
                    return new TeaLeafEntryBOImpl();
                    case PAYMENT:
                    return new PaymentBOImpl();
                    case QUERY:
                    return new QueryBOImpl();
                    case TEASELLS:
                    return new TeaSellsBOImpl();
                    case STOREDETAIL:
                    return new StoreDetailBOImpl();
                    case TEAPOWDER:
                    return new TeaPowderBOImpl();

            default:
                return null;
        }
    }
}
