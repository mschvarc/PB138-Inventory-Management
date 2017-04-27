package pb138.service.services;

import pb138.dal.entities.Item;

public interface ItemService {
    int create(Item item);

    void update(Item item);

    void delete(Item item);

    Item getById(long id);
}
