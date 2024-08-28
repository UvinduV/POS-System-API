package lk.ijse.possystemapi.dao;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.possystemapi.dto.CustomerDTO;
import lk.ijse.possystemapi.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO{
    static String SAVE_CUSTOMER = "INSERT INTO customer (CustId,CustName,CustAddress,CustContact) VALUES (?,?,?,?)";
    static String GET_CUSTOMER="Select * from customer ";
    static String UPDATE_Customer="UPDATE customer SET CustName=?,CustAddress=?,CustContact=? WHERE CustId=?";
    static String DELETE_CUSTOMER="DELETE from customer WHERE CustId=?";
    @Override
    public boolean save(Customer entity, Connection connection) {

        try {
            var ps = connection.prepareStatement(SAVE_CUSTOMER);
            ps.setString(1, entity.getCustomerId());
            ps.setString(2, entity.getCustomerName());
            ps.setString(3, entity.getCustomerAddress());
            ps.setString(4, entity.getCustomerContact());
            System.out.println("dao"+entity);

            return ps.executeUpdate() !=0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Customer> get( Connection connection) {
        /*var customerDTO=new CustomerDTO();

        try {
            var ps = connection.prepareStatement(GET_CUSTOMER);
            ps.setString(1, customerId);
            var resultSet = ps.executeQuery();
            while (resultSet.next()){
                customerDTO.setId(resultSet.getString("CustId"));
                customerDTO.setName(resultSet.getString("CustName"));
                customerDTO.setAddress(resultSet.getString("CustAddress"));
                customerDTO.setContact(resultSet.getString("CustContact"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerDTO;*/
        List<Customer> customers = new ArrayList<>();
        try {
            var preparedStatement = connection.prepareStatement(GET_CUSTOMER);
            var resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                var customer = new Customer();
                customer.setCustomerId(resultSet.getString("CustId"));
                customer.setCustomerName(resultSet.getString("CustName"));
                customer.setCustomerAddress(resultSet.getString("CustAddress"));
                customer.setCustomerContact(resultSet.getString("CustContact"));
                customers.add(customer);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return customers;

    }

    @Override
    public boolean update(String customerId, Customer entity, Connection connection) {
        try {
            var ps = connection.prepareStatement(UPDATE_Customer);
            ps.setString(1, entity.getCustomerName());
            ps.setString(2, entity.getCustomerAddress());
            ps.setString(3, entity.getCustomerContact());
            ps.setString(4, customerId);
            return ps.executeUpdate() != 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String customerId, Connection connection) {
        try{
            var ps = connection.prepareStatement(DELETE_CUSTOMER);
            ps.setString(1, customerId);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
