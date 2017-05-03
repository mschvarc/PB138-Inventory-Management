package pb138.service.facades;

import pb138.dal.entities.Item;
import pb138.service.exceptions.EntityAlreadyExistsException;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.exceptions.ServiceException;

/**
 * Created by Honza on 30.04.2017.
 * For creating item from parameters and changing the stock amount
 * I will find out whether the item with this ean exist(if getItemByEan returns null)
 * and if it does, I will call update. If it doesn't and it is called from something that may create items
 * (e.g. shipmentImporter or web, salesImporter will not be able to create new items), it creates a new item
 */
public interface ItemFacade {

    Item createItem(String name, String description, String categoryName, String categoryDescription,
                    int alertThreshold, String unit, int ean, int currentStock) throws EntityAlreadyExistsException, ServiceException;
    Item changeItem(String name, String description, String categoryName, String categoryDescription,
                      int alertThreshold, String unit, int ean, int stockDelta) throws ServiceException, EntityDoesNotExistException;
    Item getItemByEan(int ean);

    /**
     * For doing some fixes of amount from web, won't be reflected in sales
     * @param id id of product
     * @param newQuantity how the quantity should change (positive or negative number)
     * @return Item that was changed
     */
    Item changeQuantityOfItem(long id, int newQuantity) throws EntityDoesNotExistException, ServiceException;

    boolean exists(int ean);

}
