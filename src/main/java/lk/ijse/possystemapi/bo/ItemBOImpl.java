package lk.ijse.possystemapi.bo;

import lk.ijse.possystemapi.dto.ItemDTO;

import java.sql.Connection;

public class ItemBOImpl implements ItemBO {

    @Override
    public boolean saveItem(ItemDTO itemDTO, Connection connection) {
        return false;
    }

    @Override
    public ItemDTO getItem(String itemId, Connection connection) {
        return null;
    }

    @Override
    public boolean updateItem(String itemId, ItemDTO updatedItem, Connection connection) {
        return false;
    }

    @Override
    public boolean deleteItem(String itemId, Connection connection) {
        return false;
    }
}
