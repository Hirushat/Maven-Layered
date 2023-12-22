package bo.custom;

import bo.SuperBo;
import dto.CustomerDto;
import dto.tm.ItemDto;

import java.sql.SQLException;
import java.util.List;

public interface ItemBo extends SuperBo {
    boolean saveItem(ItemDto dto) throws SQLException;
    boolean updateItem(ItemDto dto) throws SQLException;
    boolean deleteItem(String id) throws SQLException;
    List<ItemDto> allItems() throws SQLException;
    ItemDto getItem(String code) throws SQLException;

}
