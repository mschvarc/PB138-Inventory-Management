package pb138.service.facades;

import pb138.dal.entities.Category;
import pb138.dal.entities.Item;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.exceptions.ServiceException;
import pb138.service.services.CategoryService;
import pb138.service.services.ItemService;
import pb138.utils.Pair;

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
    public Pair<Item, CreateOrUpdate> createOrUpdateItem(String name, String description, String categoryName,
                                                         Integer alertThreshold, String unit, long ean) throws EntityDoesNotExistException {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        if (description == null) {
            throw new IllegalArgumentException("Description must not be null");
        }
        if (categoryName == null) {
            throw new IllegalArgumentException("Category name must not be null");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit must not be null");
        }
        if (ean <= 0) {
            throw new IllegalArgumentException("Ean must be greater than 0");
        }

        Item i = itemService.getByEan(ean);
        if (i != null) {
            i = changeItem(name, description, categoryName, alertThreshold, unit, i);
            return new Pair<>(i, CreateOrUpdate.UPDATE);
        }
        i = new Item();
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
        i.setCurrentCount(0);
        return new Pair<>(i, CreateOrUpdate.CREATE);

    }

    @Override
    public Item changeItem(String newName, String newDescription, String newCategory, Integer newAlertThreshold, String newUnit, Item item) throws EntityDoesNotExistException {
        item.setName(newName);
        item.setDescription(newDescription);
        Category c = categoryService.getByName(newCategory);
        if (c == null) {
            throw new EntityDoesNotExistException("Problem when updating item " + newName + " with EAN "
                    + item.getEan() + ", category " + newCategory +" is not in db");
        }
        item.setCategory(c);
        item.setAlertThreshold(newAlertThreshold);
        item.setUnit(newUnit);
        return item;

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
    public Item updateItemFromWeb(long ean, int newAmount, Integer newThreshold, String newUnit) throws ServiceException, EntityDoesNotExistException {
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
    public Item getItemByEan(long ean) {
        return itemService.getByEan(ean);
    }



    @Override
    public Item storeItemInDb(Pair<Item, CreateOrUpdate> i) throws ServiceException {
        if (i.getValue() == CreateOrUpdate.CREATE) {
            itemService.create(i.getKey());
            return i.getKey();
        }
        itemService.update(i.getKey());
        return i.getKey();
    }


}
