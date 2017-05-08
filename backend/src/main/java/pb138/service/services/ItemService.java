package pb138.service.services;

import pb138.dal.entities.Category;
import pb138.dal.entities.Item;
import pb138.service.exceptions.ServiceException;

import java.util.List;

public interface ItemService {
    long create(Item item) throws ServiceException;

    void update(Item item) throws ServiceException;

    void delete(Item item) throws ServiceException;

    Item getById(long id) ;

    Item getByEan(long ean);

    List<Item> getByCategory(Category c);

    List<Item> getAllItems();
}
