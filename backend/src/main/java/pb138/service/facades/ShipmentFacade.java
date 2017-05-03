package pb138.service.facades;

import pb138.dal.entities.Shipment;

import java.util.Date;

/**
 * Created by Honza on 30.04.2017.
 *
 */
public interface ShipmentFacade {
    public Shipment addShipment(long productId, Date date, int arrived);
}
