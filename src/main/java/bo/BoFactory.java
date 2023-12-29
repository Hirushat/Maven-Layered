package bo;

import bo.custom.impl.CustomerBoImpl;
import bo.custom.impl.ItemBoImpl;
import bo.custom.impl.OrderDetailBoImpl;
import bo.custom.impl.OrdersBoImpl;
import dao.custom.impl.CustomerDaoImpl;
import dao.custom.impl.ItemDaoImpl;
import dao.custom.impl.OrderDaoImpl;
import dao.util.BoType;

public class BoFactory {
    private static BoFactory boFactory;
    private BoFactory(){

    }

    public static BoFactory getInstance(){
        return boFactory != null? boFactory:(boFactory=new BoFactory());
    }

    public <T extends SuperBo>T getBo(BoType type){
        switch (type){
            case CUSTOMER: return (T) new CustomerBoImpl();
            case ITEM: return (T) new ItemBoImpl();
            case ORDER: return (T) new OrdersBoImpl();
            case ORDER_DETAIL: return (T) new OrderDetailBoImpl();
        }
        return null;
    }
}