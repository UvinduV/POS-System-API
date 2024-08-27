package lk.ijse.possystemapi.bo;

import lk.ijse.possystemapi.dao.CustomerDAO;
import lk.ijse.possystemapi.dao.CustomerDAOImpl;
import lk.ijse.possystemapi.dto.CustomerDTO;
import lk.ijse.possystemapi.entity.Customer;

import java.sql.Connection;

public class CustomerBOImpl implements CustomerBO{
    CustomerDAO customerDAO= new CustomerDAOImpl();
    @Override
    public boolean saveCustomer(CustomerDTO customerDTO, Connection connection) {
        return customerDAO.saveCustomer(new Customer(customerDTO.getId(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getContact()),connection);
    }

    @Override
    public CustomerDTO getCustomer(String customerId, Connection connection) {
        return null;
    }

    @Override
    public boolean updateCustomer(String customerId, CustomerDTO updatedCustomer, Connection connection) {
        return false;
    }

    @Override
    public boolean deleteCustomer(String customerId, Connection connection) {
        return false;
    }
}
