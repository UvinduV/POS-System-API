package lk.ijse.possystemapi.Controller;


import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

        Jsonb jsonb= JsonbBuilder.create();
        ItemDTO itemDTO= jsonb.fromJson(req.getReader(), ItemDTO.class);
        itemDTO.setId(UtillProcess.generateItemId());
        System.out.println(itemDTO);

        try {
            var ps = connection.prepareStatement(SAVE_ITEM);
            ps.setString(1, itemDTO.getId());
            ps.setString(2, itemDTO.getName());
            ps.setInt(3,itemDTO.getQty());
            ps.setString(4, String.valueOf(itemDTO.getPrice()));

            if(ps.executeUpdate() != 0){
                resp.getWriter().write("item Saved");
            }else {
                resp.getWriter().write("item Not Saved");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //GetItem
        var itemDTO=new ItemDTO();
        var itemId = req.getParameter("id");

        try (var writer = resp.getWriter()){
            var ps = connection.prepareStatement(GET_ITEM);
            ps.setString(1, itemId);
            var resultSet = ps.executeQuery();
            while (resultSet.next()){
                itemDTO.setId(resultSet.getString("ItemId"));
                itemDTO.setName(resultSet.getString("ItemName"));
                itemDTO.setQty(resultSet.getInt("qty"));
                itemDTO.setPrice(resultSet.getDouble("price"));
            }
            System.out.println(itemDTO);
            System.out.println(itemDTO.getName());
            resp.setContentType("application/json");
            var jsonb = JsonbBuilder.create();
            jsonb.toJson(itemDTO,resp.getWriter());

        } catch (SQLException e) {
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
            var ps = this.connection.prepareStatement(UPDATE_ITEM);
            var itemId = req.getParameter("id");
            Jsonb jsonb = JsonbBuilder.create();
            var updatedItem = jsonb.fromJson(req.getReader(), ItemDTO.class);
            ps.setString(1, updatedItem.getName());
            ps.setInt(2,updatedItem.getQty());
            ps.setString(3, String.valueOf(updatedItem.getPrice()));
            ps.setString(4, itemId);
            if(ps.executeUpdate() != 0){
                resp.getWriter().write("Item Updated");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
                resp.getWriter().write("Item Update Failed");
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Delete Item
        var itemId = req.getParameter("id");
        try (var writer = resp.getWriter()){
            var ps = this.connection.prepareStatement(DELETE_ITEM);
            ps.setString(1, itemId);
            if(ps.executeUpdate() != 0){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                writer.write("item Deleted");
            }else {
                writer.write("item Delete Failed");
            }
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }
}
