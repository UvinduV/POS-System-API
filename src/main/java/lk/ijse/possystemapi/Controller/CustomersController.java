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
import java.util.UUID;

@WebServlet(urlPatterns = "/Customer")
public class CustomersController extends HttpServlet {
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

    }


}
