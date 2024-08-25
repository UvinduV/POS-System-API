package lk.ijse.possystemapi.Controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.possystemapi.dto.CustomerDTO;
import lk.ijse.possystemapi.dto.ItemDTO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet(urlPatterns = "/Customer")
public class CustomersController extends HttpServlet {
    private Connection connection;
    static String SAVE_CUSTOMER = "INSERT INTO customer (CustId,CustName,CustAddress,CustContact) VALUES (?,?,?,?)";
    static String GET_CUSTOMER="Select * from customer where CustId= ?";

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

        String id = UUID.randomUUID().toString();
        Jsonb jsonb= JsonbBuilder.create();
        CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);
        customerDTO.setId(id);
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
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var customerDTO=new CustomerDTO();
        var custId = req.getParameter("id");

        try (var writer = resp.getWriter()){
            var ps = connection.prepareStatement(GET_CUSTOMER);
            ps.setString(1, custId);
            var resultSet = ps.executeQuery();
            while (resultSet.next()){
                customerDTO.setId(resultSet.getString("CustId"));
                customerDTO.setName(resultSet.getString("CustName"));
                customerDTO.setAddress(resultSet.getString("CustAddress"));
                customerDTO.setContact(resultSet.getString("CustContact"));
            }
            System.out.println(customerDTO);
            System.out.println(customerDTO.getName());
            resp.setContentType("application/json");
            var jsonb = JsonbBuilder.create();
            jsonb.toJson(customerDTO,resp.getWriter());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
