package lk.ijse.possystemapi.dao;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.possystemapi.dto.CustomerDTO;
import lk.ijse.possystemapi.dto.ItemDTO;
import lk.ijse.possystemapi.entity.Customer;
import lk.ijse.possystemapi.entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO{
    static String SAVE_ITEM = "INSERT INTO item (ItemId,ItemName,qty,price) VALUES (?,?,?,?)";
    static String GET_ITEM="Select * from item ";
    static String UPDATE_ITEM="UPDATE item SET ItemName=?,qty=?,price=? WHERE ItemId=?";
    static String DELETE_ITEM="DELETE from item WHERE ItemId=?";
    @Override
    public boolean saveItem(Item entity, Connection connection) {
        try {
            var ps = connection.prepareStatement(SAVE_ITEM);
            ps.setString(1, entity.getItemId());
            ps.setString(2, entity.getItemName());
            ps.setInt(3,entity.getQty());
            ps.setString(4, String.valueOf(entity.getPrice()));

            return ps.executeUpdate() !=0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Item> getItem(Connection connection){

        List<Item> items = new ArrayList<>();
        try {
            var ps = connection.prepareStatement(GET_ITEM);
            var resultSet = ps.executeQuery();
            while (resultSet.next()){
                var item=new Item();
                item.setItemId(resultSet.getString("ItemId"));
                item.setItemName(resultSet.getString("ItemName"));
                item.setQty(resultSet.getInt("qty"));
                item.setPrice(resultSet.getDouble("price"));
                items.add(item);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items;
    }

    @Override
    public boolean updateItem(String itemId, Item item, Connection connection) {
        try {
            var ps = connection.prepareStatement(UPDATE_ITEM);
            ps.setString(1, item.getItemName());
            ps.setInt(2,item.getQty());
            ps.setString(3, String.valueOf(item.getPrice()));
            ps.setString(4, itemId);
            return ps.executeUpdate() != 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteItem(String itemId, Connection connection) {
        try {
            var ps = connection.prepareStatement(DELETE_ITEM);
            ps.setString(1, itemId);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
