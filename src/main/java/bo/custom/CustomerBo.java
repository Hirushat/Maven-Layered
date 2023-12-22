package bo.custom;

import bo.SuperBo;
import dao.SuperDao;
import dao.custom.CustomerDao;
import dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBo extends SuperBo {
    boolean saveCustomer(CustomerDto dto) throws SQLException;
    boolean updateCustomer(CustomerDto dto) throws SQLException;
    boolean deleteCustomer(String id) throws SQLException;
    List<CustomerDto> allCustomers() throws SQLException;
}
