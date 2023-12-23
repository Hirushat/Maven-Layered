package dao.custom.impl;

import dao.util.CrudUtil;
import db.DBConnection;
import dto.tm.ItemDto;
import dao.custom.ItemDao;
import entity.Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {
    @Override
    public ItemDto getItem(String code) throws SQLException {
        String sql = "SELECT * FROM item WHERE code = ? ";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1,code);
        ResultSet rst = pstm.executeQuery();
        if(rst.next()){
            return new ItemDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4)
            );
        }
        return null;
    }


    @Override
    public boolean save(Item entity) throws SQLException {
        String sql = "INSERT INTO item VALUES( ?, ?, ?, ?)";
        /*PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1,entity.getCode());
        pstm.setString(2,entity.getDescription());
        pstm.setDouble(3,entity.getUnitPrice());
        pstm.setInt(4,entity.getQtyOnHand());

        return pstm.executeUpdate()>0;*/
        return CrudUtil.execute(sql, entity.getCode(), entity.getDescription(), entity.getUnitPrice(), entity.getQtyOnHand());
    }

    @Override
    public boolean update(Item entity) throws SQLException {
        String sql = "UPDATE item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?";
       /* PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1,entity.getDescription());
        pstm.setDouble(2,entity.getUnitPrice());
        pstm.setInt(3,entity.getQtyOnHand());
        pstm.setString(4,entity.getCode());

        return pstm.executeUpdate()>0;*/
        return CrudUtil.execute(sql, entity.getDescription(), entity.getUnitPrice(), entity.getQtyOnHand(), entity.getCode());
    }

    @Override
    public boolean delete(String value) throws SQLException {
        String sql = "DELETE FROM item WHERE code = ? ";
        /*PreparedStatement pstm;

        pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1,value);*/

        return  CrudUtil.execute(sql, value);
    }

    @Override
    public List<Item> getAll() throws SQLException {
        List<Item> list = new ArrayList<>();
        String sql = "SELECT * FROM item";
        PreparedStatement pstm =  DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()){
            list.add(new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            ));
        }
        return list;
    }
}
