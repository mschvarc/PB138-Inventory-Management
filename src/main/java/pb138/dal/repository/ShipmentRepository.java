package pb138.dal.repository;

import pb138.service.dto.ShipmentDto;
import pb138.service.filters.ShipmentFilter;

public interface ShipmentRepository {
    ShipmentDto getById(long id);

    void save(ShipmentDto shipment);

    void delete(ShipmentDto shipment);

    Iterable<ShipmentDto> find(ShipmentFilter filter);
}
