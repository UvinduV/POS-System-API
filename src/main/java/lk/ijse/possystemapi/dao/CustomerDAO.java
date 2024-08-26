package lk.ijse.possystemapi.dao;

import lk.ijse.possystemapi.dto.CustomerDTO;

import java.sql.Connection;

public interface CustomerDAO {
    boolean saveCustomer(CustomerDTO customerDTO, Connection connection);
    CustomerDTO getCustomer(String customerId, Connection connection);
    boolean updateCustomer(String customerId, CustomerDTO updateCustomer, Connection connection);
    boolean deleteCustomer(String customerId, Connection connection);
}
