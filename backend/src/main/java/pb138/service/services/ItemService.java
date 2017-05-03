package pb138.service.services;

import pb138.dal.entities.Item;
import pb138.service.exceptions.ServiceException;

public interface ItemService {
    long create(Item item) throws ServiceException;

    void update(Item item) throws ServiceException;

    void delete(Item item) throws ServiceException;

    Item getById(long id) ;

    Item getByEan(int ean);
}
