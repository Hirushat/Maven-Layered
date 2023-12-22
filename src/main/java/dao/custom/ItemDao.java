package dao.custom;

import dao.CrudDao;
import dto.tm.ItemDto;
import entity.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemDao extends CrudDao<Item> {
    /*boolean saveItem(ItemDto itemDto) throws SQLException;
    boolean updateItem(ItemDto itemDto) throws SQLException;
    boolean deleteItem(String code) throws SQLException;
    List<ItemDto> allItems() throws SQLException;*/
    ItemDto getItem(String code) throws SQLException;
}
