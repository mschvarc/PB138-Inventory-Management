package services;


import dal.entities.Shipment;

public interface ShipmentService {
    int createShipment(Shipment shipment); //return new ID

    void deleteShipment(Shipment shipment);

    void updateShipment(Shipment shipment);
}
