package dal.repository;

import service.dto.ShipmentDto;
import service.filters.ShipmentFilter;

public interface ShipmentRepository {
    ShipmentDto getById(long id);

    void save(ShipmentDto shipment);

    void delete(ShipmentDto shipment);

    Iterable<ShipmentDto> find(ShipmentFilter filter);
}
