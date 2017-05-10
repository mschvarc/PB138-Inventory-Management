package pb138.dal.repository;

import pb138.dal.entities.Category;
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.filters.CategoryFilter;

/**
 * {@inheritDoc}
 */
public interface CategoryRepository extends EntityRepository<Category> {
    /**
     * {@inheritDoc}
     */
    @Override
    Category getById(long id);

    /**
     * {@inheritDoc}
     */
    @Override
    void create(Category category) throws EntityValidationException;

    /**
     * {@inheritDoc}
     */
    @Override
    void update(Category category) throws EntityValidationException;

    /**
     * {@inheritDoc}
     */
    @Override
    void delete(Category category) throws EntityValidationException;

    /**
     * Filters according to criteria specified by filter
     *
     * @param filter filter
     * @return matches
     */
    Iterable<Category> find(CategoryFilter filter);
}
