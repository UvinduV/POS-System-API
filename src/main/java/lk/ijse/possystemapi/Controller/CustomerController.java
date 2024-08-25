package lk.ijse.possystemapi.Controller;

import java.io.*;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(urlPatterns = "/customer")
public class CustomerController extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //Todo: Save student
        if(!req.getContentType().toLowerCase().startsWith("application/json")|| req.getContentType() == null){
            //send error
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
        //JSON manipulate with Parson
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        System.out.println(jsonObject.getString("name"));


    }

}