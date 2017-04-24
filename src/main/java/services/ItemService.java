package services;

import dal.entities.Item;

public interface ItemService {
    int createItem(Item item);

    void updateItem(Item item);

    void deleteItem(Item item);
}
