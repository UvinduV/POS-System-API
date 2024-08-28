package lk.ijse.possystemapi.bo;

import lk.ijse.possystemapi.dto.ItemDTO;

import java.sql.Connection;
import java.util.List;

public interface ItemBO extends SuperBO{
    boolean saveItem(ItemDTO itemDTO, Connection connection);
    List<ItemDTO> getItem(Connection connection);
    boolean updateItem(String itemId, ItemDTO updatedItem, Connection connection);
    boolean deleteItem(String itemId, Connection connection);
}
