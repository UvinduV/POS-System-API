package lk.ijse.possystemapi.dao;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.possystemapi.dto.CustomerDTO;
import lk.ijse.possystemapi.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class ItemDAOImpl implements ItemDAO{
    static String SAVE_ITEM = "INSERT INTO item (ItemId,ItemName,qty,price) VALUES (?,?,?,?)";
    static String GET_ITEM="Select * from item where ItemId= ?";
    static String UPDATE_ITEM="UPDATE item SET ItemName=?,qty=?,price=? WHERE ItemId=?";
    static String DELETE_ITEM="DELETE from item WHERE ItemId=?";
    @Override
    public boolean saveItem(ItemDTO itemDTO, Connection connection) {
        try {
            var ps = connection.prepareStatement(SAVE_ITEM);
            ps.setString(1, itemDTO.getId());
            ps.setString(2, itemDTO.getName());
            ps.setInt(3,itemDTO.getQty());
            ps.setString(4, String.valueOf(itemDTO.getPrice()));

            return ps.executeUpdate() !=0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ItemDTO getItem(String itemId, Connection connection) {
        var itemDTO=new ItemDTO();
        try {
            var ps = connection.prepareStatement(GET_ITEM);
            ps.setString(1, itemId);
            var resultSet = ps.executeQuery();
            while (resultSet.next()){
                itemDTO.setId(resultSet.getString("ItemId"));
                itemDTO.setName(resultSet.getString("ItemName"));
                itemDTO.setQty(resultSet.getInt("qty"));
                itemDTO.setPrice(resultSet.getDouble("price"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemDTO;
    }

    @Override
    public boolean updateItem(String itemId, ItemDTO updatedItem, Connection connection) {
        try {
            var ps = connection.prepareStatement(UPDATE_ITEM);
            ps.setString(1, updatedItem.getName());
            ps.setInt(2,updatedItem.getQty());
            ps.setString(3, String.valueOf(updatedItem.getPrice()));
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
