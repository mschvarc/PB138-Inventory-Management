package pb138.service.services;

import pb138.dal.entities.Category;
import pb138.dal.entities.Item;
import pb138.service.exceptions.ServiceException;

import java.util.List;

/**
 * Class for working with items
 */
public interface ItemService {
    /**
     * Creates new item
     * @param item Item to be created
     * @return id of created item
     * @throws ServiceException when there are any unexpected problems
     */
    long create(Item item) throws ServiceException;

    /**
     * Updates item
     * @param item Item to be updated
     * @throws ServiceException when there are any unexpected problems
     */
    void update(Item item) throws ServiceException;

    /**
     * Deletes item
     * @param item Item to be deleted
     * @throws ServiceException when there are any unexpected problems
     */
    void delete(Item item) throws ServiceException;

    /**
     * Return item of given id
     * @param id Id of item
     * @return item with this id, NULL if not present
     */
    Item getById(long id) ;

    /**
     * Return item of given ean
     * @param ean EAN of item
     * @return item with this EAN, NULL if not present
     */
    Item getByEan(long ean);

    /**
     * Returns all items belonging to given category
     * @param c category
     * @return all items of given category
     */
    List<Item> getByCategory(Category c);

    /**
     * Returns list of all items
     * @return list of all items
     */
    List<Item> getAllItems();
}
