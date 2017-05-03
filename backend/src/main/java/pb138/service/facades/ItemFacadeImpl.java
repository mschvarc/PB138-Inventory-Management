package pb138.service.facades;

import pb138.dal.entities.Category;
import pb138.dal.entities.Item;
import pb138.service.exceptions.EntityAlreadyExistsException;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.exceptions.ServiceException;
import pb138.service.services.CategoryService;
import pb138.service.services.ItemService;


/**
 * Created by Jan on 03.05.2017.
 *
 */
public class ItemFacadeImpl implements ItemFacade {
    private ItemService itemService;
    private CategoryService categoryService;

    public ItemFacadeImpl(ItemService itemService, CategoryService categoryService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
    }

    @Override
    public Item createItem(String name, String description, String categoryName, String categoryDescription,
                           int alertThreshold, String unit, int ean, int currentStock)  throws EntityAlreadyExistsException, ServiceException {
        if (itemService.getByEan(ean) != null) {
            throw new EntityAlreadyExistsException("This item already exists in database");
        }
        Item i = new Item();
        i.setName(name);
        i.setDescription(description);
        Category c = categoryService.getByName(categoryName);
        if (c == null) {
            c = new Category();
            c.setName(categoryName);
            c.setDescription(categoryDescription);
            categoryService.create(c);
        } else {
            c.setDescription(description);
            categoryService.update(c);
        }
        i.setCategory(c);
        i.setAlertThreshold(alertThreshold);
        i.setUnit(unit);
        i.setEan(ean);
        i.setCurrentCount(currentStock);
        itemService.create(i);
        return i;
    }

    @Override
    public Item changeItem(String name, String description, String categoryName,
                           String categoryDescription, int alertThreshold, String unit, int ean, int stockDelta)
            throws ServiceException, EntityDoesNotExistException {
        Item i = getItemByEan(ean);
        if (i == null) {
            //exceptions will be changed later, i was just lazy to create new and i didnt want to have it underlined
            //It shouldn't happen anyway, since this method will be always called when knowing the item exist
            throw new EntityDoesNotExistException("Item doesn't exist");
        }
        Category c = i.getCategory();
        if (c.getName().equalsIgnoreCase(categoryName)) {
            c.setDescription(categoryDescription);
            categoryService.update(c);
        } else {
            c = new Category();
            c.setName(categoryName);
            c.setDescription(categoryDescription);
            categoryService.create(c);
        }
        i.setCategory(c);
        i.setAlertThreshold(alertThreshold);
        i.setUnit(unit);
        i.setCurrentCount(i.getCurrentCount() + /*it will be negative number in case of sales*/stockDelta);
        return i;
    }

    @Override
    public Item changeQuantityOfItem(long id, int newQuantity) throws EntityDoesNotExistException, ServiceException {
        Item i  = itemService.getById(id);
        if (i == null) {
            throw new EntityDoesNotExistException("Item does not exist");
        }
        i.setCurrentCount(newQuantity);
        itemService.update(i);
        return i;
    }

    @Override
    public Item getItemByEan(int ean) {
        return itemService.getByEan(ean);
    }

    @Override
    public boolean exists(int ean) {
        return getItemByEan(ean) != null;
    }
}
