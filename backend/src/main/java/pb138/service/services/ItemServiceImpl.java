package pb138.service.services;

import com.google.common.collect.Iterables;
import pb138.dal.entities.Item;
import pb138.dal.repository.CategoryRepository;
import pb138.dal.repository.ItemRepository;
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.exceptions.ServiceException;
import pb138.service.filters.ItemFilter;

/**
 * Created by Honza on 30.04.2017.
 *
 */
public class ItemServiceImpl implements ItemService{
    private ItemRepository itemRepository;
    private CategoryRepository categoryRepository;

    public ItemServiceImpl(ItemRepository itemRepository, CategoryRepository categoryRepository ) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public long create(Item item) throws ServiceException {
        try {
            long catId = item.getCategory().getId();
            if (categoryRepository.getById(catId) == null) {
                throw new ServiceException("Category does not exist");
            }
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
    public Item getByEan(int ean) {
        ItemFilter filter = new ItemFilter();
        filter.setEan(ean);
        Iterable<Item> result = itemRepository.find(filter);
        return Iterables.getFirst(result, null);
    }
}
