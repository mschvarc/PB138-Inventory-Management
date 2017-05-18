package pb138.service.facades;

import pb138.dal.entities.Item;
import pb138.dal.entities.Shipment;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.exceptions.ServiceException;
import pb138.service.services.ItemService;
import pb138.service.services.ShipmentService;

import java.util.Date;
import java.util.List;

/**
 * Created by Jan on 06.05.2017.
 * Implements shipment facade
 */
public class ShipmentFacadeImpl  implements ShipmentFacade{
    private ShipmentService shipmentService;
    private ItemService itemService;

    /**
     * Constructor.
     *
     * @param shipmentService shipmentService
     * @param itemService itemService
     */
    public ShipmentFacadeImpl(ShipmentService shipmentService, ItemService itemService) {
        this.shipmentService = shipmentService;
        this.itemService = itemService;
    }

    @Override
    public Shipment addShipment(long ean, Date date, int arrived) throws EntityDoesNotExistException {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (arrived < 0) {
            throw new IllegalArgumentException("Arrived cannot be negative");
        }
        Item i = itemService.getByEan(ean);
        if (i == null) {
            throw new EntityDoesNotExistException("Cannot create shipment for item with EAN "
                    + ean +", item with this EAN does not exist");
        }
        Shipment s = new Shipment();
        s.setDateImported(date);
        s.setItem(i);
        s.setQuantityImported(arrived);
        return s;

    }

    @Override
    public Shipment storeShipmentInDb(Shipment s) throws ServiceException {
        Item i = s.getItem();
        i.setCurrentCount(i.getCurrentCount() + s.getQuantityImported());
        itemService.update(i);
        shipmentService.create(s);
        return s;
    }

    @Override
    public List<Shipment> getAllShipments() {
        return shipmentService.getAllShipments();
    }
}
