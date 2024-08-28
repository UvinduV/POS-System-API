package lk.ijse.possystemapi.Controller;

import jakarta.json.JsonException;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.possystemapi.bo.PlaceOrderBO;
import lk.ijse.possystemapi.bo.PlaceOrderBOImpl;
import lk.ijse.possystemapi.dto.CustomerDTO;
import lk.ijse.possystemapi.dto.PlaceOrderDTO;
import lk.ijse.possystemapi.utill.UtillProcess;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/Order")
public class PlaceOrderController extends HttpServlet {
    PlaceOrderBO placeOrderBO= new PlaceOrderBOImpl();
    Connection connection;
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
        try (var writer = resp.getWriter()){
            Jsonb jsonb= JsonbBuilder.create();
            PlaceOrderDTO placeOrderDTO = jsonb.fromJson(req.getReader(), PlaceOrderDTO.class);

            if (placeOrderDTO == null ) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect data");
                return;
            }

            var reciveOrder = placeOrderBO.placeOrder(placeOrderDTO,connection);
            System.out.println("place order controller"+ reciveOrder);

            if (reciveOrder) {
                writer.write("order place Successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }else {
                writer.write("order place Failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

        } catch (JsonException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
