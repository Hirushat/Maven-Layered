package model.impl;

import db.DBConnection;
import dto.CustomerDto;
import dto.tm.ItemDto;
import model.ItemModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModelImpl implements ItemModel {
    @Override
    public boolean saveItem(ItemDto itemDto) throws SQLException {

        return false;
    }

    @Override
    public boolean updateItem(ItemDto itemDto) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteItem(String code) throws SQLException {
        String sql = "DELETE FROM item WHERE code = ? ";
        PreparedStatement pstm;

        pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1,code);

        return  pstm.executeUpdate()>0;
    }

    @Override
    public List<ItemDto> allItems() throws SQLException {
        List<ItemDto> list = new ArrayList<>();
        String sql = "SELECT * FROM item";
        PreparedStatement pstm =  DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()){
            list.add(new ItemDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            ));
        }
        return list;
    }

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


}
