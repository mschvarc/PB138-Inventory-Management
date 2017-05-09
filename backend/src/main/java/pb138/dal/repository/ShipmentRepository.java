package pb138.dal.repository;

import pb138.dal.entities.Shipment;
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.filters.ShipmentFilter;

/**
 * {@inheritDoc}
 */
public interface ShipmentRepository extends EntityRepository<Shipment> {
    /**
     * {@inheritDoc}
     */
    @Override
    Shipment getById(long id);

    /**
     * {@inheritDoc}
     */
    @Override
    void create(Shipment shipment) throws EntityValidationException;

    /**
     * {@inheritDoc}
     */
    @Override
    void update(Shipment shipment) throws EntityValidationException;

    /**
     * {@inheritDoc}
     */
    @Override
    void delete(Shipment shipment) throws EntityValidationException;

    /**
     * Filters according to criteria specified by filter
     *
     * @param filter filter
     * @return matches
     */
    Iterable<Shipment> find(ShipmentFilter filter);
}
