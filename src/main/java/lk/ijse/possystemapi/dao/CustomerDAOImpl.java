package lk.ijse.possystemapi.dao;

import lk.ijse.possystemapi.dto.CustomerDTO;

import java.sql.Connection;

public class CustomerDAOImpl implements CustomerDAO{
    @Override
    public boolean saveCustomer(CustomerDTO customerDTO, Connection connection) {
        return false;
    }

    @Override
    public CustomerDTO getCustomer(String customerId, Connection connection) {
        return null;
    }

    @Override
    public boolean updateCustomer(String customerId, CustomerDTO updateCustomer, Connection connection) {
        return false;
    }

    @Override
    public boolean deleteCustomer(String customerId, Connection connection) {
        return false;
    }
}
