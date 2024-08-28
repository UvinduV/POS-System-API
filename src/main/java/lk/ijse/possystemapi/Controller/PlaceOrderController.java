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
import lk.ijse.possystemapi.bo.Custom.PlaceOrderBO;
import lk.ijse.possystemapi.bo.Impl.PlaceOrderBOImpl;
import lk.ijse.possystemapi.dto.PlaceOrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/Order")
public class PlaceOrderController extends HttpServlet {
    static Logger logger= LoggerFactory.getLogger(PlaceOrderController.class);
    PlaceOrderBO placeOrderBO= (PlaceOrderBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.ORDER);
    Connection connection;
    @Override
    public void init() throws ServletException {
        logger.info("Initializing place order Controller with call inti method");
        try {
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
        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
        try (var writer = resp.getWriter()){
            Jsonb jsonb= JsonbBuilder.create();
            System.out.println("go to dto");
            PlaceOrderDTO placeOrderDTO = jsonb.fromJson(req.getReader(),PlaceOrderDTO.class);

            if (placeOrderDTO == null ) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect data");
                return;
            }
            System.out.println("placeODto : "+placeOrderDTO);
            writer.write("order place Successfully");

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
