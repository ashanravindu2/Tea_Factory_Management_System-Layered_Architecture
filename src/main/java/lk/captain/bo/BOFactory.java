package lk.captain.bo;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBoFactory(){
        return (boFactory==null)?boFactory=new BOFactory():(boFactory);
    }

    public enum BOTypes {
        ADDCUSTOMER
    }
    public SuperBO getBOTypes(BOTypes boTypes) {
        switch (boTypes) {
            case ADDCUSTOMER:
                return new AddCustomerBOImpl();
            default:
                return null;
        }
    }
}
