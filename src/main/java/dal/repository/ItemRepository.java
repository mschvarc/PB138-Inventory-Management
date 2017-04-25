package dal.repository;

import service.dto.ItemDto;
import service.filters.ItemFilter;

public interface ItemRepository {
    ItemDto getById(long id);

    void save(ItemDto item);

    void delete(ItemDto item);

    Iterable<ItemDto> find(ItemFilter criteria);
}
