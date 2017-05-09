package pb138.dal.repository;

import pb138.dal.entities.Item;
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.filters.ItemFilter;

/**
 * {@inheritDoc}
 */
public interface ItemRepository extends EntityRepository<Item> {
    /**
     * {@inheritDoc}
     */
    @Override
    Item getById(long id);

    /**
     * {@inheritDoc}
     */
    @Override
    void create(Item item) throws EntityValidationException;

    /**
     * {@inheritDoc}
     */
    @Override
    void update(Item item) throws EntityValidationException;

    /**
     * {@inheritDoc}
     */
    @Override
    void delete(Item item) throws EntityValidationException;

    /**
     * Filters according to criteria specified by filter
     *
     * @param filter filter
     * @return matches
     */
    Iterable<Item> find(ItemFilter filter);
}
