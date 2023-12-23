package dao.custom.impl;

import dao.util.CrudUtil;
import db.DBConnection;
import dto.OrderDetailsDto;
import dao.custom.OrderDetailsDao;
import entity.OrderDetail;
import entity.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailsDaoImpl implements OrderDetailsDao {

    @Override
    public boolean save(OrderDetailsDto entity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(OrderDetailsDto entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String value) throws SQLException {
        return false;
    }

    @Override
    public List<OrderDetailsDto> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean saveOrderDetails(List<OrderDetailsDto> list) throws SQLException {
        for (OrderDetailsDto dto:list) {
            String sql = "INSERT INTO orderdetail VALUES(?,?,?,?)";
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,dto.getOrderId());
            pstm.setString(2,dto.getItemCode());
            pstm.setInt(3,dto.getQty());
            pstm.setDouble(4,dto.getUnitPrice());

            if(!(pstm.executeUpdate()>0)){
                return false;
            }
        }
        return true;
    }
}


