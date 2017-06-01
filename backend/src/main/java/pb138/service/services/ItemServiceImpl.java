package pb138.service.services;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import pb138.dal.entities.Category;
import pb138.dal.entities.Item;
import pb138.dal.repository.ItemRepository;
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.exceptions.ServiceException;
import pb138.service.filters.ItemFilter;

import java.util.List;

/**
 * Implements item service
 *
 */
public class ItemServiceImpl implements ItemService{
    private ItemRepository itemRepository;

    /**
     * Constructor
     * @param itemRepository item repository
     */
    public ItemServiceImpl(ItemRepository itemRepository ) {
        this.itemRepository = itemRepository;

    }

    @Override
    public long create(Item item) throws ServiceException {
        try {
            itemRepository.create(item);
            return item.getId();
        } catch (EntityValidationException e) {
            throw new ServiceException("Problem with validating the item", e);
        }
    }

    @Override
    public void update(Item item) throws ServiceException {
        try {
            itemRepository.update(item);
        } catch (EntityValidationException e) {
            throw new ServiceException("Problem with validating the item", e);
        }
    }

    @Override
    public void delete(Item item) throws ServiceException {
        try {
            itemRepository.delete(item);
        } catch (EntityValidationException e) {
            throw new ServiceException("Problem with validating the item", e);
        }
    }

    @Override
    public Item getById(long id) {
        return itemRepository.getById(id);
    }

    @Override
    public Item getByEan(long ean) {
        ItemFilter filter = new ItemFilter();
        filter.setEan(ean);
        Iterable<Item> result = itemRepository.find(filter);
        return Iterables.getFirst(result, null);
    }

    @Override
    public List<Item> getByCategory(Category c) {
        ItemFilter filter = new ItemFilter();
        filter.setCategory(c);
        Iterable<Item> result = itemRepository.find(filter);
        return Lists.newArrayList(result);
    }

    @Override
    public List<Item> getAllItems() {
        ItemFilter filter = new ItemFilter();
        Iterable<Item> result = itemRepository.find(filter);
        return Lists.newArrayList(result);
    }
}
