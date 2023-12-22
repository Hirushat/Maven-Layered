package bo.custom;

import bo.SuperBo;
import dto.OrderDto;

import java.sql.SQLException;
import java.util.List;

public interface OrdersBo extends SuperBo {
    boolean saveOrder(OrderDto dto) throws SQLException;
    boolean updateOrder(OrderDto dto) throws SQLException;
    boolean deleteOrder(String id) throws SQLException;
    List<OrderDto> allOrders() throws SQLException;
    OrderDto lastOrder() throws SQLException;
}
