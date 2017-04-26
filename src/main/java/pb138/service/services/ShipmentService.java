package pb138.service.services;


import pb138.service.dto.ShipmentDto;

public interface ShipmentService {

    int createShipment(ShipmentDto shipment); //return new ID

    void deleteShipment(ShipmentDto shipment);

    void updateShipment(ShipmentDto shipment);

    ShipmentDto getShipmentById(long id);

}
