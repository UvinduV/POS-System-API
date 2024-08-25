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

        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
        /*JsonReader reader1= Json.createReader(req.getReader());
        JsonObject jsonObject= reader1.readObject();
        System.out.println(jsonObject.getString("email"));*/

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


}
