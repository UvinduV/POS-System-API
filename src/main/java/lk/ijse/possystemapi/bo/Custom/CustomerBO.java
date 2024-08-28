package lk.ijse.possystemapi.bo.Custom;

import lk.ijse.possystemapi.bo.SuperBO;
import lk.ijse.possystemapi.dto.CustomerDTO;

import java.sql.Connection;
import java.util.List;

public interface CustomerBO extends SuperBO {
    boolean saveCustomer(CustomerDTO customerDTO, Connection connection);
    List<CustomerDTO> getCustomer(Connection connection);
    boolean updateCustomer(String customerId, CustomerDTO updatedCustomer, Connection connection);
    boolean deleteCustomer(String customerId, Connection connection);

}
