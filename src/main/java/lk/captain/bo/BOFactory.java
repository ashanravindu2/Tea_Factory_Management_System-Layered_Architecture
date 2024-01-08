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
        ,WORKER,TEASUPPLIER,WOOD
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
            default:
                return null;
        }
    }
}
