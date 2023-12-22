package dao.custom.impl;

import dao.util.CrudUtil;
import db.DBConnection;
import dto.OrderDto;
import dao.custom.OrderDetailsDao;
import dao.custom.OrderDao;
import entity.Customer;
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
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            String sql = "INSERT INTO orders VALUES(?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,entity.getId());
            pstm.setString(2,entity.getDate());
            pstm.setString(3,entity.getCustomerId());

            if (pstm.executeUpdate()>0){
                    connection.commit();
                    return true;

            }
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        } finally{
            connection.setAutoCommit(true);
        }
        return false;
    }

    @Override
    public boolean update(Orders entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String value) throws SQLException {
        String sql = "DELETE FROM orders WHERE id = ? ";
        /*PreparedStatement pstm;

        pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1,value);

        return  pstm.executeUpdate()>0;*/
        return CrudUtil.execute(sql, value);
    }

    @Override
    public List<Orders> getAll() throws SQLException {
        List<Orders> list = new ArrayList<>();

        String sql = "SELECT * FROM orders";
        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()){
            list.add(new Orders(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            ));
        }
        return list;
    }

    @Override
    public Orders lastOrder() throws SQLException {
        String sql = "SELECT * FROM orders ORDER BY id DESC LIMIT 1";
        //PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()){
            return new Orders(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
        }
        return null;
    }
}
