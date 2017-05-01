package pb138.service.services;


import pb138.dal.entities.Shipment;
import pb138.service.exceptions.ServiceException;

public interface ShipmentService {

    long create(Shipment shipment) throws ServiceException; //return new ID

    void delete(Shipment shipment) throws ServiceException;

    void update(Shipment shipment) throws ServiceException;

    Shipment getById(long id);
}
