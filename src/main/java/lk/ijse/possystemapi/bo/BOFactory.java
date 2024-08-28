package lk.ijse.possystemapi.bo;


import lk.ijse.possystemapi.bo.Impl.CustomerBOImpl;
import lk.ijse.possystemapi.bo.Impl.ItemBOImpl;
import lk.ijse.possystemapi.bo.Impl.PlaceOrderBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getBOFactory(){
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER,ITEM,ORDER
    }

    public SuperBO getBo(BOTypes types){
        switch (types){
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case ORDER:
                return new PlaceOrderBOImpl();
            default:
                return null;
        }
    }
}
