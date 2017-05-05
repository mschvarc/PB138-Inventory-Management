package pb138.service.facades;

import pb138.dal.entities.Shipment;

import java.util.Date;

/**
 * Created by Honza on 30.04.2017.
 * Facade for working with shipments;
 */
public interface ShipmentFacade {
    Shipment addShipment(int ean, Date date, int arrived);
}
