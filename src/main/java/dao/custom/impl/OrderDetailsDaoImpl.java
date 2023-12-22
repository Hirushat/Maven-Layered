package dao.custom.impl;

import dao.util.CrudUtil;
import db.DBConnection;
import dto.OrderDetailsDto;
import dao.custom.OrderDetailsDao;
import entity.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailsDaoImpl implements OrderDetailsDao {
    @Override
    public boolean saveOrderDetails(List<OrderDetail> list) throws SQLException {
        boolean isDetailsSaved = true;
        for (OrderDetail orderDetail:list) {
            String sql = "INSERT INTO orderdetail VALUES(?,?,?,?)";
            /*PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,orderDetail.getOrderId());
            pstm.setString(2,orderDetail.getItemCode());
            pstm.setInt(3,orderDetail.getQty());
            pstm.setDouble(4,orderDetail.getUnitPrice());*/

            if(CrudUtil.execute(sql, orderDetail.getOrderId(), orderDetail.getItemCode(), orderDetail.getQty(), orderDetail.getUnitPrice())){
                isDetailsSaved = true;
            }else{
                isDetailsSaved = false;
            }
        }
        return isDetailsSaved;
    }

    @Override
    public boolean save(OrderDetail entity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(OrderDetail entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String value) throws SQLException {
        return false;
    }

    @Override
    public List<OrderDetail> getAll() throws SQLException {
        return null;
    }
}

