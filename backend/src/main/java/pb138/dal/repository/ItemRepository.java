package pb138.dal.repository;

import pb138.dal.entities.Item;
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.filters.ItemFilter;

public interface ItemRepository {
    Item getById(long id);

    void create(Item item) throws EntityValidationException;

    void update(Item item) throws EntityValidationException;

    void delete(Item item) throws EntityValidationException;

    Iterable<Item> find(ItemFilter filter);
}
