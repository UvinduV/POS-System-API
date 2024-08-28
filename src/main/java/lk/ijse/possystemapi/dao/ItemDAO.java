package lk.ijse.possystemapi.dao;

import lk.ijse.possystemapi.dto.CustomerDTO;
import lk.ijse.possystemapi.dto.ItemDTO;
import lk.ijse.possystemapi.entity.Customer;
import lk.ijse.possystemapi.entity.Item;

import java.sql.Connection;
import java.util.List;

public interface ItemDAO {
    boolean saveItem(Item entity, Connection connection);
    List<Item> getItem(Connection connection);
    boolean updateItem(String itemId, Item item, Connection connection);
    boolean deleteItem(String itemId, Connection connection);

}
