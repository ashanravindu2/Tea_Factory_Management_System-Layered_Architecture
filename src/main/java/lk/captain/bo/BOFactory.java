package lk.captain.bo;

import lk.captain.bo.custom.AddCustomerBOImpl;
import lk.captain.bo.custom.TeaCollectorBOImpl;
import lk.captain.bo.custom.TeaSupplierBOImpl;
import lk.captain.bo.custom.WorkerBOImpl;

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
        ,WORKER,TEASUPPLIER
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
            default:
                return null;
        }
    }
}
