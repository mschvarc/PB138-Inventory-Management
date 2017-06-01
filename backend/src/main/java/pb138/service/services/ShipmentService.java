package pb138.service.services;


import pb138.dal.entities.Shipment;
import pb138.service.exceptions.ServiceException;

import java.util.List;

/**
 * Class for working with shipments
 */
public interface ShipmentService {

    /**
     * Creates new shipment
     * @param shipment Shipment to be created
     * @return id of created shipment
     * @throws ServiceException when there are any unexpected problems
     */
    long create(Shipment shipment) throws ServiceException; //return new ID

    /**
     * Deletes shipment
     * @param shipment Shipment to be deleted
     * @throws ServiceException when there are any unexpected problems
     */
    void delete(Shipment shipment) throws ServiceException;

    /**
     * Updates shipment
     * @param shipment Shipment to be updated
     * @throws ServiceException when there are any unexpected problems
     */
    void update(Shipment shipment) throws ServiceException;

    /**
     * Return shipment of given id
     * @param id Id of shipment
     * @return shipment with this id, NULL if not present
     */
    Shipment getById(long id);

    /**
     * Get all shipments
     * @return all shipments
     */
    List<Shipment> getAllShipments();
}
