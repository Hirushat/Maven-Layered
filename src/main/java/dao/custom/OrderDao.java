package dao.custom;

import dao.CrudDao;
import dto.OrderDto;
import entity.Orders;

import java.sql.SQLException;

public interface OrderDao extends CrudDao<Orders> {
   // boolean saveOrder(Orders orders) throws SQLException;
    Orders lastOrder() throws SQLException;
   // boolean deleteItem(String code) throws SQLException;



}
