package pb138.dal.repository;

import pb138.service.dto.ItemDto;
import pb138.service.filters.ItemFilter;

public interface ItemRepository {
    ItemDto getById(long id);

    void save(ItemDto item);

    void delete(ItemDto item);

    Iterable<ItemDto> find(ItemFilter criteria);
}
