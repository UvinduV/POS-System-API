package lk.ijse.possystemapi.dao.Custom;

import lk.ijse.possystemapi.dao.CrudDAO;
import lk.ijse.possystemapi.dto.CustomerDTO;
import lk.ijse.possystemapi.dto.ItemDTO;
import lk.ijse.possystemapi.entity.Customer;
import lk.ijse.possystemapi.entity.Item;

import java.sql.Connection;
import java.util.List;

public interface ItemDAO extends CrudDAO<Item> {
    boolean save(Item entity, Connection connection);
    List<Item> get(Connection connection);
    boolean update(String itemId, Item entity, Connection connection);
    boolean delete(String itemId, Connection connection);

}
