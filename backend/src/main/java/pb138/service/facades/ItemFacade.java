package pb138.service.facades;

import pb138.dal.entities.Category;
import pb138.dal.entities.Item;
import pb138.service.exceptions.AlreadyExistsException;
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
                    int alertThreshold, String unit, int ean, int currentStock) throws AlreadyExistsException, ServiceException;
    Item changeItem(String name, String description, String categoryName, String categoryDescription,
                      int alertThreshold, String unit, int ean, int stockDelta) throws AlreadyExistsException, ServiceException;
    Item getItemByEan(int ean);

    boolean exists(int ean);

}
