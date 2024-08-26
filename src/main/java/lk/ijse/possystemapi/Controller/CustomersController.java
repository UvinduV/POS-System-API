package lk.ijse.possystemapi.Controller;

import jakarta.json.JsonException;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.possystemapi.dao.CustomerDAO;
import lk.ijse.possystemapi.dao.CustomerDAOImpl;
import lk.ijse.possystemapi.dto.CustomerDTO;
import lk.ijse.possystemapi.dto.ItemDTO;
import lk.ijse.possystemapi.utill.UtillProcess;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet(urlPatterns = "/Customer")
public class CustomersController extends HttpServlet {
    CustomerDAO customerDAO = new CustomerDAOImpl();
    private Connection connection;
    static String SAVE_CUSTOMER = "INSERT INTO customer (CustId,CustName,CustAddress,CustContact) VALUES (?,?,?,?)";
    static String GET_CUSTOMER="Select * from customer where CustId= ?";
    static String UPDATE_Customer="UPDATE customer SET CustName=?,CustAddress=?,CustContact=? WHERE CustId=?";
    static String DELETE_CUSTOMER="DELETE from customer WHERE CustId=?";

    @Override
    public void init() throws ServletException {
        try {
            var driverCalss = getServletContext().getInitParameter("driver-class");
            var dbUrl = getServletContext().getInitParameter("dbURL");
            var userName = getServletContext().getInitParameter("dbUserName");
            var password = getServletContext().getInitParameter("dbPassword");
            Class.forName(driverCalss);
            this.connection =  DriverManager.getConnection(dbUrl,userName,password);
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //save customer
        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }

        /*String id = UUID.randomUUID().toString();*/
        /*Jsonb jsonb= JsonbBuilder.create();
        CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);
        customerDTO.setId(UtillProcess.generateCustomerId());
        System.out.println(customerDTO);

        try {
            var ps = connection.prepareStatement(SAVE_CUSTOMER);
            ps.setString(1, customerDTO.getId());
            ps.setString(2, customerDTO.getName());
            ps.setString(3, customerDTO.getAddress());
            ps.setString(4, customerDTO.getContact());

            if(ps.executeUpdate() != 0){
                resp.getWriter().write("Customer Saved");
            }else {
                resp.getWriter().write("customer Not Saved");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/

        try (var writer = resp.getWriter()){
            Jsonb jsonb= JsonbBuilder.create();
            CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            customerDTO.setId(UtillProcess.generateCustomerId());
            System.out.println(customerDTO);
            var customer=customerDAO.saveCustomer(customerDTO,connection);
            System.out.println("cust"+customer);

            if (customer) {
                writer.write("Customer Save Successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }else {
                writer.write("Customer Save Failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (JsonException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Get Customer
        /*var customerDTO=new CustomerDTO();*/
        var custId = req.getParameter("id");

        try (var writer = resp.getWriter()){
            /*var ps = connection.prepareStatement(GET_CUSTOMER);
            ps.setString(1, custId);
            var resultSet = ps.executeQuery();
            while (resultSet.next()){
                customerDTO.setId(resultSet.getString("CustId"));
                customerDTO.setName(resultSet.getString("CustName"));
                customerDTO.setAddress(resultSet.getString("CustAddress"));
                customerDTO.setContact(resultSet.getString("CustContact"));
            }*/
            var customer = customerDAO.getCustomer(custId,connection);

            System.out.println(customer);
            System.out.println(customer.getName());
            resp.setContentType("application/json");
            var jsonb = JsonbBuilder.create();
            jsonb.toJson(customer,resp.getWriter());

        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Update Customer
        if(!req.getContentType().toLowerCase().startsWith("application/json")|| req.getContentType() == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        /*var ps = this.connection.prepareStatement(UPDATE_Customer);*/
        var custId = req.getParameter("id");
        Jsonb jsonb = JsonbBuilder.create();
        var updatedCustomer = jsonb.fromJson(req.getReader(), CustomerDTO.class);

            /*ps.setString(1, updatedCustomer.getName());
            ps.setString(2, updatedCustomer.getAddress());
            ps.setString(3, updatedCustomer.getContact());
            ps.setString(4, custId);*/

        if(customerDAO.updateCustomer(custId,updatedCustomer,connection)){
            resp.getWriter().write("Customer Updated");
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }else {
            resp.getWriter().write("Customer Update Failed");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Delete Customer
        var custId = req.getParameter("id");
        try (var writer = resp.getWriter()){
            /*var ps = this.connection.prepareStatement(DELETE_CUSTOMER);
            ps.setString(1, custId);*/
            if(customerDAO.deleteCustomer(custId,connection)){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                writer.write("Customer Deleted");
            }else {
                writer.write("customer Delete Failed");
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }
}
