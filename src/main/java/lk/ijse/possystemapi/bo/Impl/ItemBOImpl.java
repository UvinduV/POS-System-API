package lk.ijse.possystemapi.bo.Impl;

import lk.ijse.possystemapi.bo.ItemBO;
import lk.ijse.possystemapi.dao.Custom.ItemDAO;
import lk.ijse.possystemapi.dao.Impl.ItemDAOImpl;
import lk.ijse.possystemapi.dto.ItemDTO;
import lk.ijse.possystemapi.entity.Item;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO= new ItemDAOImpl();

    @Override
    public boolean saveItem(ItemDTO itemDTO, Connection connection) {
        return itemDAO.save(new Item(itemDTO.getId(),itemDTO.getName(),itemDTO.getQty(), itemDTO.getPrice()),connection) ;
    }

    @Override
    public List<ItemDTO> getItem(Connection connection) {
        List<Item> items = itemDAO.get(connection);
        List<ItemDTO> itemDTOS = new ArrayList<>();

        for(Item item : items){

            ItemDTO itemDTO=new ItemDTO();
            itemDTO.setId(item.getItemId());
            itemDTO.setName(item.getItemName());
            itemDTO.setQty(item.getQty());
            itemDTO.setPrice(item.getPrice());
            itemDTOS.add(itemDTO);
        }
        return itemDTOS;
    }

    @Override
    public boolean updateItem(String itemId, ItemDTO updatedItem, Connection connection) {

        Item item = new Item(itemId, updatedItem.getName(), updatedItem.getQty(), updatedItem.getPrice());
        return itemDAO.update(itemId,item,connection);
    }

    @Override
    public boolean deleteItem(String itemId, Connection connection) {
        return itemDAO.delete(itemId,connection);
    }
}
