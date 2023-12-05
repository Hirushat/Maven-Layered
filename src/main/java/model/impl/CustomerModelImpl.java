package model.impl;

import db.DBConnection;
import dto.CustomerDto;
import dto.tm.CustomerTm;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import model.CustomerModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerModelImpl implements CustomerModel{
    @Override
    public boolean saveCustomer(CustomerDto customerDto) throws SQLException {

        String sql = "INSERT INTO customer VALUES( ?, ?, ?, ?)";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1,customerDto.getId());
        pstm.setString(2,customerDto.getName());
        pstm.setString(3,customerDto.getAddress());
        pstm.setDouble(4,customerDto.getSalary());

        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean updateCustomer(CustomerDto customerDto) throws SQLException {
        String sql = "UPDATE customer SET name=?, address=?, salary=? WHERE id=?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1,customerDto.getName());
        pstm.setString(2,customerDto.getAddress());
        pstm.setDouble(3,customerDto.getSalary());
        pstm.setString(4,customerDto.getId());

        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean deleteCustomer(String customerDto) throws SQLException {
        String sql = "DELETE FROM Customer WHERE id = ?";

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, customerDto);

        return pstm.executeUpdate()>0;
    }

    @Override
    public List<CustomerDto> allCustomers() throws SQLException {
        List<CustomerDto> list = new ArrayList<>();

        String sql = "SELECT * FROM customer";
        PreparedStatement pstm =  DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()){
            list.add(new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            ));
        }
        return list;
    }

    @Override
    public CustomerDto searchCustomer(String id){
        return null;
    }
}
