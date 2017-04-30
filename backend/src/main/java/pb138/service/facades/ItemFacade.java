package pb138.service.facades;

import pb138.dal.entities.Category;
import pb138.dal.entities.Item;

/**
 * Created by Honza on 30.04.2017.
 * For creating item from parameters and changing the stock amount
 */
public interface ItemFacade {

    int createItem(String name, String description, String categoryName, String categoryDescription, int alertThreshold, String unit, int ean, int currentStock);
    int changeAmount(int ean, int change);
    Item getItemByEan(int ean);

}
