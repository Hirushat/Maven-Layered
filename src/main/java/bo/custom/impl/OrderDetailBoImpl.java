package bo.custom.impl;

import bo.custom.OrderDetailBo;
import dao.DaoFactory;
import dao.custom.OrderDao;
import dao.custom.OrderDetailsDao;
import dao.custom.impl.OrderDaoImpl;
import dao.custom.impl.OrderDetailsDaoImpl;
import dao.util.DaoType;
import dto.CustomerDto;
import dto.OrderDetailsDto;
import dto.OrderDto;
import entity.Customer;
import entity.OrderDetail;
import entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailBoImpl implements OrderDetailBo {
    private OrderDetailsDao orderDetailsDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);
    @Override
    public boolean saveOrderDetails(List<OrderDetailsDto> list) throws SQLException {

        List<OrderDetail> detaiList = new ArrayList<>();

        for (OrderDetailsDto orderDetailDto: list) {
            detaiList.add(new OrderDetail(
                    orderDetailDto.getOrderId(),
                    orderDetailDto.getItemCode(),
                    orderDetailDto.getQty(),
                    orderDetailDto.getUnitPrice()
            ));
        }
        return orderDetailsDao.saveOrderDetails(detaiList);
    }

    @Override
    public List<OrderDetail> OrderDetailsList(List<OrderDetailsDto> list) {
        List<OrderDetail> detaiList = new ArrayList<>();

        for (OrderDetailsDto orderDetailDto: list) {
            detaiList.add(new OrderDetail(
                    orderDetailDto.getOrderId(),
                    orderDetailDto.getItemCode(),
                    orderDetailDto.getQty(),
                    orderDetailDto.getUnitPrice()
            ));
        }
        return detaiList;
    }

}

