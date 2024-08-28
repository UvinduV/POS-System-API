package lk.ijse.possystemapi.bo;

import lk.ijse.possystemapi.dao.Custom.CustomerDAO;
import lk.ijse.possystemapi.dao.Impl.CustomerDAOImpl;
import lk.ijse.possystemapi.dto.CustomerDTO;
import lk.ijse.possystemapi.entity.Customer;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO{
    CustomerDAO customerDAO= new CustomerDAOImpl();
    @Override
    public boolean saveCustomer(CustomerDTO customerDTO, Connection connection) {
        return customerDAO.save(new Customer(customerDTO.getId(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getContact()),connection);
    }

    @Override
    public List<CustomerDTO> getCustomer(Connection connection) {
        List<Customer> customers = customerDAO.get(connection);
        List<CustomerDTO> customerDTOS = new ArrayList<>();

        for(Customer customer : customers){
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(customer.getCustomerId());
            customerDTO.setName(customer.getCustomerName());
            customerDTO.setAddress(customer.getCustomerAddress());
            customerDTO.setContact(customer.getCustomerContact());
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    @Override
    public boolean updateCustomer(String customerId, CustomerDTO updatedCustomer, Connection connection) {
        Customer customer = new Customer(customerId, updatedCustomer.getName(), updatedCustomer.getAddress(), updatedCustomer.getContact());
        return customerDAO.update(customerId, customer, connection);
    }

    @Override
    public boolean deleteCustomer(String customerId, Connection connection) {
        return customerDAO.delete(customerId,connection);
    }
}
