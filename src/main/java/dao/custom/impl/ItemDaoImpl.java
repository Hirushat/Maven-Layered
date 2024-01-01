package dao.custom.impl;

import dao.util.CrudUtil;
import dao.util.HibernateUtil;
import db.DBConnection;
import dto.tm.ItemDto;
import dao.custom.ItemDao;
import entity.Customer;
import entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {
    @Override
    public ItemDto getItem(String code) throws SQLException {
        Session session = HibernateUtil.getSession();
        Query<Item> query = session.createQuery("FROM Item WHERE code = :code");
        query.setParameter("code", code);
        Item item = query.uniqueResult();

        if(item != null){
            return new ItemDto(
                    item.getCode(),
                    item.getDescription(),
                    item.getUnitPrice(),
                    item.getQtyOnHand()
            );
        }
        return null;
       /* String sql = "SELECT * FROM item WHERE code = ? ";
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
        return null;*/
    }


    @Override
    public boolean save(Item entity) throws SQLException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
        /*String sql = "INSERT INTO item VALUES( ?, ?, ?, ?)";
        *//*PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1,entity.getCode());
        pstm.setString(2,entity.getDescription());
        pstm.setDouble(3,entity.getUnitPrice());
        pstm.setInt(4,entity.getQtyOnHand());

        return pstm.executeUpdate()>0;*//*
        return CrudUtil.execute(sql, entity.getCode(), entity.getDescription(), entity.getUnitPrice(), entity.getQtyOnHand());*/
    }

    @Override
    public boolean update(Item entity) throws SQLException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Item item = session.find(Item.class, entity.getCode());
        item.setDescription(entity.getDescription());
        item.setQtyOnHand(entity.getQtyOnHand());
        item.setUnitPrice(entity.getUnitPrice());
        session.save(item);
        transaction.commit();
        return true;
        /*String sql = "UPDATE item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?";
       *//* PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1,entity.getDescription());
        pstm.setDouble(2,entity.getUnitPrice());
        pstm.setInt(3,entity.getQtyOnHand());
        pstm.setString(4,entity.getCode());

        return pstm.executeUpdate()>0;*//*
        return CrudUtil.execute(sql, entity.getDescription(), entity.getUnitPrice(), entity.getQtyOnHand(), entity.getCode());*/
    }

    @Override
    public boolean delete(String value) throws SQLException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.find(Item.class,value));
        transaction.commit();
        return true;
        /*String sql = "DELETE FROM item WHERE code = ? ";
        *//*PreparedStatement pstm;

        pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1,value);*//*

        return  CrudUtil.execute(sql, value);*/
    }

    @Override
    public List<Item> getAll() throws SQLException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Item");
        List<Item> list = query.list();
        /*List<Item> list = new ArrayList<>();
        String sql = "SELECT * FROM item";
        //PreparedStatement pstm =  DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()){
            list.add(new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            ));
        }*/
        return list;
    }
}
