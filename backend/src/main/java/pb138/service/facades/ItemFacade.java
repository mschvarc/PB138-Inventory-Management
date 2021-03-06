package pb138.service.facades;

import pb138.dal.entities.Item;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.exceptions.ServiceException;
import pb138.utils.Pair;

import java.util.List;

/**
 * For creating item from parameters and changing the stock amount
 * I will find out whether the item with this ean exist(if getItemByEan returns null)
 * and if it does, I will call update. If it doesn't and it is called from something that may create items
 * (e.g. shipmentImporter or web, salesImporter will not be able to create new items), it creates a new item
 */
public interface ItemFacade {

    /**
     * Creates a new item, or updates it if it already exist but doesn't do the actual storing in db
     * @param name name of item
     * @param description description of item, null if there should be none
     * @param categoryName name of category of this item, must be already in db, cause @EntityDoesNotExistException if it is not there
     * @param alertThreshold alert threshold for item, null if there is none
     * @param unit unit for measuring the item
     * @param ean ean of item, unique identifier
     * @return Item that was created
     * @throws EntityDoesNotExistException if category is not in db
     *
     */
    Pair<Item, CreateOrUpdate> createOrUpdateItem(String name, String description, String categoryName, Integer alertThreshold, String unit, long ean)
            throws EntityDoesNotExistException;

    /**
     * Updates existing item, but doesn't store it in db
     * @param newName New name of item
     * @param newDescription New description of item, null if there should be none
     * @param newCategory New category of item, must be in db, cause @EntityDoesNotExistException if it is not there
     * @param newAlertThreshold New alert threshold of item, or null
     * @param newUnit New unit of item
     * @param item item to be changed
     * @return Item that was changed
     *
     * @throws EntityDoesNotExistException if category is not in db
     */
    Item changeItem(String newName, String newDescription, String newCategory, Integer newAlertThreshold,
                    String newUnit, Item item) throws EntityDoesNotExistException;

    /**
     * Gets item by its ean
     * @param ean ean of item
     * @return Item with given ean
     */
    Item getItemByEan(long ean);


    /**
     * Return all items
     * @return All items in db
     */
    List<Item> getAllItems();

    /**
     * Return all items with given category
     * @param categoryName name of the category
     * @return list of items belonging to the category
     * @throws EntityDoesNotExistException if category is not in the db
     */
    List<Item> getAllItemsByCategory(String categoryName) throws EntityDoesNotExistException;


    /**
     * For updating items from web interface
     * @param ean ean of item to be updated
     * @param newAmount new amount of item
     * @param newThreshold new threshold, null if it will be left unfilled or something
     * @param newUnit new unit
     * @return item that was updated
     * @throws ServiceException if something goes wrong on the db layer and service layer cannot deal with it
     * @throws EntityDoesNotExistException if item with this ean is not in db
     */
    Item updateItemFromWeb(long ean, int newAmount, Integer newThreshold, String newUnit) throws ServiceException, EntityDoesNotExistException;


    /**
     * Item will be created in db
     * @param i item to be created
     * @return item that was created
     * @throws ServiceException if something goes wrong on the db layer and service layer cannot deal with it
     */
    Item storeItemInDb(Pair<Item, CreateOrUpdate> i) throws ServiceException;





}
