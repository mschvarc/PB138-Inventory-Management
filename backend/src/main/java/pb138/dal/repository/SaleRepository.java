package pb138.dal.repository;

import pb138.dal.entities.Sale;
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.filters.SaleFilter;

/**
 * {@inheritDoc}
 */
public interface SaleRepository extends EntityRepository<Sale> {
    /**
     * {@inheritDoc}
     */
    @Override
    Sale getById(long id);

    /**
     * {@inheritDoc}
     */
    @Override
    void create(Sale sale) throws EntityValidationException;

    /**
     * {@inheritDoc}
     */
    @Override
    void update(Sale sale) throws EntityValidationException;

    /**
     * {@inheritDoc}
     */
    @Override
    void delete(Sale sale) throws EntityValidationException;

    /**
     * Filters according to criteria specified by filter
     *
     * @param filter filter
     * @return matches
     */
    Iterable<Sale> find(SaleFilter filter);
}
