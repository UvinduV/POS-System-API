package lk.ijse.possystemapi.bo;

import lk.ijse.possystemapi.dto.ItemDTO;

import java.sql.Connection;

public interface ItemBO {
    boolean saveItem(ItemDTO itemDTO, Connection connection);
    ItemDTO getItem(String itemId, Connection connection);
    boolean updateItem(String itemId, ItemDTO updatedItem, Connection connection);
    boolean deleteItem(String itemId, Connection connection);
}
