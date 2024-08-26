package lk.ijse.possystemapi.dao;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.possystemapi.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class CustomerDAOImpl implements CustomerDAO{
    static String SAVE_CUSTOMER = "INSERT INTO customer (CustId,CustName,CustAddress,CustContact) VALUES (?,?,?,?)";
    static String GET_CUSTOMER="Select * from customer where CustId= ?";
    static String UPDATE_Customer="UPDATE customer SET CustName=?,CustAddress=?,CustContact=? WHERE CustId=?";
    static String DELETE_CUSTOMER="DELETE from customer WHERE CustId=?";
    @Override
    public boolean saveCustomer(CustomerDTO customerDTO, Connection connection) {

        try {
            var ps = connection.prepareStatement(SAVE_CUSTOMER);
            ps.setString(1, customerDTO.getId());
            ps.setString(2, customerDTO.getName());
            ps.setString(3, customerDTO.getAddress());
            ps.setString(4, customerDTO.getContact());
            System.out.println("dao"+customerDTO);

            return ps.executeUpdate() !=0;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CustomerDTO getCustomer(String customerId, Connection connection) {
        var customerDTO=new CustomerDTO();

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
        return customerDTO;

    }

    @Override
    public boolean updateCustomer(String customerId, CustomerDTO updatedCustomer, Connection connection) {
        try {
            var ps = connection.prepareStatement(UPDATE_Customer);
            ps.setString(1, updatedCustomer.getName());
            ps.setString(2, updatedCustomer.getAddress());
            ps.setString(3, updatedCustomer.getContact());
            ps.setString(4, customerId);
            return ps.executeUpdate() != 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCustomer(String customerId, Connection connection) {
        try{
            var ps = connection.prepareStatement(DELETE_CUSTOMER);
            ps.setString(1, customerId);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
