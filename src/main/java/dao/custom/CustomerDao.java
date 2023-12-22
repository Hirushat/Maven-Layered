package dao.custom;

import dao.CrudDao;
import dto.CustomerDto;
import entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDao extends CrudDao <Customer> {
   /* boolean saveCustomer(CustomerDto customerDto) throws SQLException;
    boolean updateCustomer(CustomerDto customerDto) throws SQLException;
    boolean deleteCustomer(String customerDto) throws SQLException;
    List<CustomerDto> allCustomers() throws SQLException;*/
    CustomerDto searchCustomer(String id);

}
