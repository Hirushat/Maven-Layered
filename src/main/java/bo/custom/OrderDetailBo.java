package bo.custom;

import bo.SuperBo;
import dto.OrderDetailsDto;
import entity.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailBo extends SuperBo {
    public boolean saveOrderDetails(List<OrderDetailsDto> list) throws SQLException;
    public List<OrderDetail> OrderDetailsList(List<OrderDetailsDto> list);


}
