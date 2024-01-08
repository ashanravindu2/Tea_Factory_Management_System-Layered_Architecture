package lk.captain.bo;

import lk.captain.bo.custom.AddCustomerBOImpl;
import lk.captain.bo.custom.TeaCollectorBOImpl;

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
    }
    public SuperBO getBOTypes(BOTypes boTypes) {
        switch (boTypes) {
            case ADDCUSTOMER:
                return new AddCustomerBOImpl();
                case TEACOLLECTOR:
                return new TeaCollectorBOImpl();
            default:
                return null;
        }
    }
}
