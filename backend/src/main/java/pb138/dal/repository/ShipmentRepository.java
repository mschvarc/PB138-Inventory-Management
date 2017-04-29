package pb138.dal.repository;

import pb138.dal.entities.Shipment;
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.filters.ShipmentFilter;

public interface ShipmentRepository {
    Shipment getById(long id);

    void create(Shipment shipment) throws EntityValidationException;

    void update(Shipment shipment) throws EntityValidationException;

    void delete(Shipment shipment) throws EntityValidationException;

    Iterable<Shipment> find(ShipmentFilter filter);
}
