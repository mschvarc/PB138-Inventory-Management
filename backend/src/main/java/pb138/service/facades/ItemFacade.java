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
     * Creates a new item
     * @param name name of item
     * @param description description of item, null if there should be none
     * @param categoryName name of category of this item, must be already in db, cause @EntityDoesNotExistException if it is not there
     * @param alertThreshold alert threshold for item, null if there is none
     * @param unit unit for measuring the item
     * @param ean ean of item, unique identifier
     * @return Item that was created
     * @throws EntityDoesNotExistException if category is not in db
     * @throws ServiceException if something goes wrong on the db layer and service layer cannot deal with it
     */
    Item createItem(String name, String description, String categoryName, Integer alertThreshold, String unit, int ean)
            throws EntityDoesNotExistException, ServiceException;

    /**
     * Updates existing item
     * @param ean ean of item to be changed
     * @param newName New name of item
     * @param newDescription New description of item, null if there should be none
     * @param newCategory New category of item, must be in db, cause @EntityDoesNotExistException if it is not there
     * @param newAlertThreshold New alert threshold of item, or null
     * @param newUnit New unit of item
     * @return Item that was changed
     * @throws ServiceException if something goes wrong on the db layer and service layer cannot deal with it
     * @throws EntityDoesNotExistException if category is not in db
     */
    Item changeItem(int ean, String newName, String newDescription, String newCategory, Integer newAlertThreshold,
                    String newUnit) throws ServiceException, EntityDoesNotExistException;

    /**
     * Gets item by its ean
     * @param ean ean of item
     * @return Item with given ean
     */
    Item getItemByEan(int ean);


    /**
     * Return all items
     * @return All items in db
     * @throws ServiceException if something goes wrong on the db layer and service layer cannot deal with it
     */
    List<Item> getAllItems() throws ServiceException;

    /**
     * Return all items with given category
     * @param categoryName name of the category
     * @return list of items belonging to the category
     * @throws ServiceException if something goes wrong on the db layer and service layer cannot deal with it
     * @throws EntityDoesNotExistException if category is not in the db
     */
    List<Item> getAllItemsByCategory(String categoryName) throws ServiceException, EntityDoesNotExistException;


    /**
     *
     * @param ean
     * @param newAmount
     * @param newThreshold
     * @param newUnit
     * @return
     * @throws ServiceException
     * @throws EntityDoesNotExistException
     */
    Item updateItemFromWeb(int ean, int newAmount, Integer newThreshold, String newUnit) throws ServiceException, EntityDoesNotExistException;

    boolean exists(int ean);

}
