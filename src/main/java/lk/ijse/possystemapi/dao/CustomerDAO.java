package lk.ijse.possystemapi.dao;

import lk.ijse.possystemapi.dto.CustomerDTO;
import lk.ijse.possystemapi.entity.Customer;

import java.sql.Connection;
import java.util.List;

public interface CustomerDAO {
    boolean saveCustomer(Customer entity, Connection connection);
    List<Customer> getCustomer(Connection connection);
    boolean updateCustomer(String customerId, Customer customer, Connection connection);
    boolean deleteCustomer(String customerId, Connection connection);

}
