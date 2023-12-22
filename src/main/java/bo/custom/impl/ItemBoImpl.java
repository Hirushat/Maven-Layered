package bo.custom.impl;

import bo.custom.ItemBo;
import dao.DaoFactory;
import dao.custom.ItemDao;
import dao.custom.impl.ItemDaoImpl;
import dao.util.DaoType;
import dto.CustomerDto;
import dto.tm.ItemDto;
import entity.Customer;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBoImpl implements ItemBo {

    private ItemDao itemDao = DaoFactory.getInstance().getDao(DaoType.ITEM);

    @Override
    public boolean saveItem(ItemDto dto) throws SQLException {
        return itemDao.save(new Item(
                dto.getCode(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getQty()
        ));
    }

    @Override
    public boolean updateItem(ItemDto dto) throws SQLException {
        return itemDao.update(new Item(
                dto.getCode(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getQty()
        ));
    }

    @Override
    public boolean deleteItem(String id) throws SQLException {
        return itemDao.delete(id);
    }

    @Override
    public List<ItemDto> allItems() throws SQLException {
        List<Item> entityList = itemDao.getAll();
        List<ItemDto> list = new ArrayList<>();
        for ( Item item: entityList) {
            list.add(new ItemDto(
                    item.getCode(),
                    item.getDescription(),
                    item.getUnitPrice(),
                    item.getQtyOnHand()
            ));
        }
        return list;
    }

    @Override
    public ItemDto getItem(String code) throws SQLException {
        return itemDao.getItem(code);
    }
}
