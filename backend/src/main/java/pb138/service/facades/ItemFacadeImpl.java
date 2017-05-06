package pb138.service.facades;

import pb138.dal.entities.Category;
import pb138.dal.entities.Item;
import pb138.service.exceptions.EntityAlreadyExistsException;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.exceptions.ServiceException;
import pb138.service.filters.ItemFilter;
import pb138.service.services.CategoryService;
import pb138.service.services.ItemService;

import java.util.List;


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
    public Item createItem(String name, String description, String categoryName,
                           Integer alertThreshold, String unit, int ean) throws EntityDoesNotExistException {
        if (exists(ean)) {
            return changeItem(ean, name, description, categoryName, alertThreshold, unit);
        }
        Item i = new Item();
        i.setName(name);
        i.setDescription(description);
        Category c = categoryService.getByName(categoryName);
        if (c == null) {
            throw new EntityDoesNotExistException("Problem when creating item " + name + " with EAN "
                    + ean + ", category " + categoryName +" is not in db");
        }
        i.setCategory(c);
        i.setAlertThreshold(alertThreshold);
        i.setUnit(unit);
        i.setEan(ean);
        return i;

    }

    @Override
    public Item changeItem(int ean, String newName, String newDescription, String newCategory, Integer newAlertThreshold, String newUnit) throws EntityDoesNotExistException {
        Item i = itemService.getByEan(ean);
        if (i == null) {
            throw new EntityDoesNotExistException("This item doesn't exist");
        }
        i.setName(newName);
        i.setDescription(newDescription);
        Category c = categoryService.getByName(newCategory);
        if (c == null) {
            throw new EntityDoesNotExistException("Problem when updating item " + newName + " with EAN "
                    + ean + ", category " + newCategory +" is not in db");
        }
        i.setCategory(c);
        i.setAlertThreshold(newAlertThreshold);
        i.setUnit(newUnit);
        return i;

    }

    @Override
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @Override
    public List<Item> getAllItemsByCategory(String categoryName) throws EntityDoesNotExistException {
        Category c = categoryService.getByName(categoryName);
        if (c == null) {
            throw new EntityDoesNotExistException("This category doesn't exist");
        }
        return itemService.getByCategory(c);

    }

    @Override
    public Item updateItemFromWeb(int ean, int newAmount, Integer newThreshold, String newUnit) throws ServiceException, EntityDoesNotExistException {
        Item i = itemService.getByEan(ean);
        if (i == null) {
            throw new EntityDoesNotExistException("This item does not exist");
        }
        i.setCurrentCount(newAmount);
        i.setAlertThreshold(newThreshold);
        i.setUnit(newUnit);
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

    @Override
    public Item storeItemInDb(Item i) throws ServiceException {
        itemService.create(i);
        return i;
    }

    @Override
    public Item updateItemInDb(Item i) throws ServiceException {
        itemService.update(i);
        return i;
    }
}
