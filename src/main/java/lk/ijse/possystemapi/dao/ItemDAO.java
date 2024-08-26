package lk.ijse.possystemapi.dao;

import lk.ijse.possystemapi.dto.CustomerDTO;
import lk.ijse.possystemapi.dto.ItemDTO;

import java.sql.Connection;

public interface ItemDAO {
    boolean saveItem(ItemDTO itemDTO, Connection connection);
    ItemDTO getItem(String itemId, Connection connection);
    boolean updateItem(String itemId, ItemDTO updatedItem, Connection connection);
    boolean deleteItem(String itemId, Connection connection);
}
