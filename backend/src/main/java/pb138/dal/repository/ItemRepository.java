package pb138.dal.repository;

import pb138.dal.entities.Item;
import pb138.service.filters.ItemFilter;

public interface ItemRepository {
    Item getById(long id);

    void create(Item item);

    void update(Item item);

    void delete(Item item);

    Iterable<Item> find(ItemFilter filter);
}
