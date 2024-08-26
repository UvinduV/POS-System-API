package lk.ijse.possystemapi.Controller;


import jakarta.json.Json;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.possystemapi.dao.CustomerDAO;
import lk.ijse.possystemapi.dao.CustomerDAOImpl;
import lk.ijse.possystemapi.dao.ItemDAO;
import lk.ijse.possystemapi.dao.ItemDAOImpl;
import lk.ijse.possystemapi.dto.CustomerDTO;
import lk.ijse.possystemapi.dto.ItemDTO;
import lk.ijse.possystemapi.utill.UtillProcess;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet(urlPatterns = "/Item")
public class ItemController extends HttpServlet {
    ItemDAO itemDAO =new ItemDAOImpl();
    private Connection connection;
    static String SAVE_ITEM = "INSERT INTO item (ItemId,ItemName,qty,price) VALUES (?,?,?,?)";
    static String GET_ITEM="Select * from item where ItemId= ?";
    static String UPDATE_ITEM="UPDATE item SET ItemName=?,qty=?,price=? WHERE ItemId=?";
    static String DELETE_ITEM="DELETE from item WHERE ItemId=?";
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
            ItemDTO itemDTO= jsonb.fromJson(req.getReader(), ItemDTO.class);
            itemDTO.setId(UtillProcess.generateItemId());
            System.out.println(itemDTO);

            var item=itemDAO.saveItem(itemDTO,connection);

            if (item) {
                writer.write("Item Save Successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }else {
                writer.write("Item Save Failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (JsonException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //GetItem

        var itemId = req.getParameter("id");

        try (var writer = resp.getWriter()){
            var item=itemDAO.getItem(itemId,connection);

            System.out.println(item);
            System.out.println(item.getName());
            resp.setContentType("application/json");
            var jsonb = JsonbBuilder.create();
            jsonb.toJson(item,resp.getWriter());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Update Item
        if(!req.getContentType().toLowerCase().startsWith("application/json")|| req.getContentType() == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        try {

            var itemId = req.getParameter("id");
            Jsonb jsonb = JsonbBuilder.create();
            var updatedItem = jsonb.fromJson(req.getReader(), ItemDTO.class);
            if(itemDAO.updateItem(itemId,updatedItem,connection)){
                resp.getWriter().write("Item Updated");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
                resp.getWriter().write("Item Update Failed");
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Delete Item
        var itemId = req.getParameter("id");
        try (var writer = resp.getWriter()){
            if(itemDAO.deleteItem(itemId,connection)){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                writer.write("item Deleted");
            }else {
                writer.write("item Delete Failed");
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }
}
