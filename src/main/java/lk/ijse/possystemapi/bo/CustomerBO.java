package lk.ijse.possystemapi.bo;

import lk.ijse.possystemapi.dto.CustomerDTO;

import java.sql.Connection;

public interface CustomerBO {
    boolean saveCustomer(CustomerDTO customerDTO, Connection connection);
    CustomerDTO getCustomer(String customerId, Connection connection);
    boolean updateCustomer(String customerId, CustomerDTO updatedCustomer, Connection connection);
    boolean deleteCustomer(String customerId, Connection connection);

}
