package dao.custom;

import dao.CrudDao;
import dto.OrderDetailsDto;
import dto.OrderDto;
import entity.Orders;

import java.sql.SQLException;

public interface OrderDao extends CrudDao<Orders> {
   // boolean saveOrder(Orders orders) throws SQLException;
    OrderDto lastOrder() throws SQLException;
    boolean saveOrder(OrderDto dto) throws SQLException;
   // boolean deleteItem(String code) throws SQLException;



}
