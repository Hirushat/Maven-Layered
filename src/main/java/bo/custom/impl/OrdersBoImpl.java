package bo.custom.impl;

import bo.custom.OrdersBo;
import dao.DaoFactory;
import dao.custom.OrderDao;
import dao.custom.OrderDetailsDao;
import dao.custom.impl.OrderDaoImpl;
import dao.custom.impl.OrderDetailsDaoImpl;
import dao.util.DaoType;
import db.DBConnection;
import dto.CustomerDto;
import dto.OrderDetailsDto;
import dto.OrderDto;
import entity.Customer;
import entity.Item;
import entity.OrderDetail;
import entity.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersBoImpl implements OrdersBo {

    private OrderDao orderDao = DaoFactory.getInstance().getDao(DaoType.ORDER);

    @Override
    public boolean saveOrder(OrderDto dto) throws SQLException {
        return orderDao.save(new Orders(
                dto.getOrderId(),
                dto.getDate(),
                dto.getCustId()
        ));
    }

    @Override
    public boolean updateOrder(OrderDto dto) throws SQLException {
        return orderDao.update(new Orders(
                dto.getOrderId(),
                dto.getDate(),
                dto.getCustId()
        ));
    }

    @Override
    public boolean deleteOrder(String id) throws SQLException {
        return orderDao.delete(id);
    }

    @Override
    public List<OrderDto> allOrders() throws SQLException {
        List<Orders> entityList = orderDao.getAll();
        List<OrderDto> list = new ArrayList<>();
        for (Orders order: entityList) {
            list.add(new OrderDto(
                    order.getId(),
                    order.getDate(),
                    order.getCustomerId(),
                    null
            ));
        }
        return list;
    }

    @Override
    public OrderDto lastOrder() throws SQLException {
        String sql = "SELECT * FROM orders ORDER BY id DESC LIMIT 1";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            return new OrderDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    null
            );
        }
        return null;
    }
}
