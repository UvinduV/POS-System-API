package lk.ijse.possystemapi.Controller;

import jakarta.json.JsonException;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.possystemapi.bo.BOFactory;
import lk.ijse.possystemapi.bo.Custom.CustomerBO;
import lk.ijse.possystemapi.bo.Impl.CustomerBOImpl;
import lk.ijse.possystemapi.dto.CustomerDTO;
import lk.ijse.possystemapi.utill.UtillProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/Customer")
public class CustomersController extends HttpServlet {
    static Logger logger= LoggerFactory.getLogger(CustomersController.class);
    CustomerBO customerBO = (CustomerBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.CUSTOMER);;
    private Connection connection;

    @Override
    public void init() throws ServletException {
        logger.info("Initializing customer Controller with call inti method");
        try {
            /*var driverCalss = getServletContext().getInitParameter("driver-class");
            var dbUrl = getServletContext().getInitParameter("dbURL");
            var userName = getServletContext().getInitParameter("dbUserName");
            var password = getServletContext().getInitParameter("dbPassword");
            Class.forName(driverCalss);
            this.connection =  DriverManager.getConnection(dbUrl,userName,password);*/
            var ctx=new InitialContext();
            DataSource pool = (DataSource)ctx.lookup("java:comp/env/jdbc/POSSystemApi");
            this.connection= pool.getConnection();
        }catch (SQLException | NamingException e){
            logger.error("Init failed with", e.getMessage());
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

        try (var writer = resp.getWriter()){
            Jsonb jsonb= JsonbBuilder.create();
            CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            customerDTO.setId(UtillProcess.generateCustomerId());
            System.out.println(customerDTO);
            var customer = customerBO.saveCustomer(customerDTO,connection);
            System.out.println("cust controller"+customer);

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

        var custId = req.getParameter("id");

        try (var writer = resp.getWriter()){

            List<CustomerDTO>customer = customerBO.getCustomer(connection);

            System.out.println(customer);
            /*System.out.println(customer);*/
            resp.setContentType("application/json");
            var jsonb = JsonbBuilder.create();
            jsonb.toJson(customer,writer);

        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Update Customer
        if(!req.getContentType().toLowerCase().startsWith("application/json")|| req.getContentType() == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }


        var custId = req.getParameter("id");
        Jsonb jsonb = JsonbBuilder.create();
        var updatedCustomer = jsonb.fromJson(req.getReader(), CustomerDTO.class);


        if(customerBO.updateCustomer(custId,updatedCustomer,connection)){
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
            if(customerBO.deleteCustomer(custId,connection)){
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
