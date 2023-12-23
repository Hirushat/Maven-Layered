package dao.custom.impl;

import dao.util.CrudUtil;
import db.DBConnection;
import dto.OrderDetailsDto;
import dao.custom.OrderDetailsDao;
import dao.custom.OrderDao;
import dto.OrderDto;
import entity.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    OrderDetailsDao orderDetailsDao = new OrderDetailsDaoImpl();

    @Override
    public boolean save(Orders entity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Orders entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String value) throws SQLException {
        String sql = "DELETE FROM orders WHERE id = ? ";
        PreparedStatement pstm;

        pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, value);

        return pstm.executeUpdate() > 0;
    }

    @Override
    public List<Orders> getAll() throws SQLException {
        List<Orders> list = new ArrayList<>();

        String sql = "SELECT * FROM orders";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            list.add(new Orders(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            ));
        }
        return list;
    }

    @Override
    public OrderDto lastOrder() throws SQLException {
        String sql = "SELECT * FROM orders ORDER BY id DESC LIMIT 1";
       // PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()) {
            return new OrderDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    null
            );
        }
        return null;
    }

    @Override
    public boolean saveOrder(OrderDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            String sql = "INSERT INTO orders VALUES(?,?,?)";

            if (CrudUtil.execute(sql, dto.getOrderId(), dto.getDate(), dto.getCustId())){
                boolean isDetailSaved = orderDetailsDao.saveOrderDetails(dto.getList());
                if (isDetailSaved){
                    connection.commit();
                    return true;
                }
            }
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        } finally{
            connection.setAutoCommit(true);
        }
        return false;
    }
}
