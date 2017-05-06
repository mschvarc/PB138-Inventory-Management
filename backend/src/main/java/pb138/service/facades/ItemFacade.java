package pb138.service.facades;

import pb138.dal.entities.Item;
import pb138.service.exceptions.EntityAlreadyExistsException;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.exceptions.ServiceException;

import java.util.Collection;
import java.util.List;

/**
 * Created by Honza on 30.04.2017.
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
    Item createItem(String name, String description, String categoryName, Integer alertThreshold, String unit, int ean)
            throws EntityDoesNotExistException;

    /**
     * Updates existing item, but doesn't store it in db
     * @param ean ean of item to be changed
     * @param newName New name of item
     * @param newDescription New description of item, null if there should be none
     * @param newCategory New category of item, must be in db, cause @EntityDoesNotExistException if it is not there
     * @param newAlertThreshold New alert threshold of item, or null
     * @param newUnit New unit of item
     * @return Item that was changed
     *
     * @throws EntityDoesNotExistException if category is not in db
     */
    Item changeItem(int ean, String newName, String newDescription, String newCategory, Integer newAlertThreshold,
                    String newUnit) throws EntityDoesNotExistException;

    /**
     * Gets item by its ean
     * @param ean ean of item
     * @return Item with given ean
     */
    Item getItemByEan(int ean);


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
    Item updateItemFromWeb(int ean, int newAmount, Integer newThreshold, String newUnit) throws ServiceException, EntityDoesNotExistException;

    /**
     * return if item with this ean exists
     * @param ean ean of item
     * @return true if it exists, false otherwise
     */
    boolean exists(int ean);

    /**
     * Item will be created in db
     * @param i item to be created
     * @return item that was created
     * @throws ServiceException if something goes wrong on the db layer and service layer cannot deal with it
     */
    Item storeItemInDb(Item i) throws ServiceException;

    /**
     * Item will be updated in db
     * @param i item to be updated
     * @return item that was updated
     * @throws ServiceException if something goes wrong on the db layer and service layer cannot deal with it
     */
    Item updateItemInDb(Item i) throws ServiceException;



}
