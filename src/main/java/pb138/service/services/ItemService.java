package pb138.service.services;

import pb138.service.dto.ItemDto;

public interface ItemService {
    int createItem(ItemDto item);

    void updateItem(ItemDto item);

    void deleteItem(ItemDto item);

    ItemDto getItemById(long id);
}
