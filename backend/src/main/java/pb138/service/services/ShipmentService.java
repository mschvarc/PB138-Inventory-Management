package pb138.service.services;


import pb138.dal.entities.Shipment;

public interface ShipmentService {

    int create(Shipment shipment); //return new ID

    void delete(Shipment shipment);

    void update(Shipment shipment);

    Shipment getById(long id);
}
