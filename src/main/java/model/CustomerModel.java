package model;

import dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerModel {
    boolean saveCustomer(CustomerDto customerDto) throws SQLException;
    boolean updateCustomer(CustomerDto customerDto) throws SQLException;
    boolean deleteCustomer(String customerDto) throws SQLException;
    List<CustomerDto> allCustomers() throws SQLException;
    CustomerDto searchCustomer(String id);

}
