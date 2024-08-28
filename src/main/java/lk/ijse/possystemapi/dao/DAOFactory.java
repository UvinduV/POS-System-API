package lk.ijse.possystemapi.dao;


import lk.ijse.possystemapi.dao.Impl.CustomerDAOImpl;
import lk.ijse.possystemapi.dao.Impl.ItemDAOImpl;
import lk.ijse.possystemapi.dao.Impl.OrderDAOImpl;
import lk.ijse.possystemapi.dao.Impl.OrderDetailDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){

    }

    public static DAOFactory getDaoFactory(){
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes{
        CUSTOMER,ITEM,ORDER,ORDER_DETAIL
    }

    public SuperDAO getDao(DAOTypes daoTypes){
        switch (daoTypes){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDER_DETAIL:
                return new OrderDetailDAOImpl();
            default:
                return null;
        }
    }

}
