package pb138.service.facades;

import javafx.util.Pair;
import pb138.dal.entities.Shipment;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.exceptions.ServiceException;

import java.util.Date;

/**
 * Created by Honza on 30.04.2017.
 * Facade for working with shipments;
 */
public interface ShipmentFacade {
    /**
     * Creates a new shipment, does not store it in db
     * @param ean ean of item that was shipped
     * @param date date when shipment arrived
     * @param arrived how many items arrived
     * @return shipment that was created together with number of items that arrived
     * @throws EntityDoesNotExistException when item does not exist
     */
    Pair<Shipment, Integer> addShipment(int ean, Date date, int arrived) throws EntityDoesNotExistException;

    /**
     * Actually stores correctly created shipment in the db
     * @param s shipment to be stored
     * @param count how many items arrived
     * @return shipment that was stored
     * @throws ServiceException if something goes wrong on the db layer and service layer cannot deal with it
     */
    Shipment storeShipmentInDb(Shipment s, int count) throws ServiceException;
}
