package pb138.dal.repository;

import pb138.dal.entities.Shipment;
import pb138.service.filters.ShipmentFilter;

public interface ShipmentRepository {
    Shipment getById(long id);

    void create(Shipment shipment);

    void update(Shipment shipment);

    void delete(Shipment shipment);

    Iterable<Shipment> find(ShipmentFilter filter);
}
