package pb138.service.facades;

import javafx.util.Pair;
import pb138.dal.entities.Item;
import pb138.dal.entities.Shipment;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.exceptions.ServiceException;
import pb138.service.services.ItemService;
import pb138.service.services.ShipmentService;

import java.util.Date;

/**
 * Created by Jan on 06.05.2017.
 * Implements shipment facade
 */
public class ShipmentFacadeImpl  implements ShipmentFacade{
    private ShipmentService shipmentService;
    private ItemService itemService;

    public ShipmentFacadeImpl(ShipmentService shipmentService, ItemService itemService) {
        this.shipmentService = shipmentService;
        this.itemService = itemService;
    }

    @Override
    public Pair<Shipment, Integer> addShipment(int ean, Date date, int arrived) throws EntityDoesNotExistException {
        Item i = itemService.getByEan(ean);
        if (i == null) {
            throw new EntityDoesNotExistException("Cannot create shipment for item with EAN "
                    + ean +", item with this EAN does not exist");
        }
        Shipment s = new Shipment();
        s.setDateImported(date);
        s.setItem(i);
        s.setQuantityImported(arrived);
        i.setCurrentCount(i.getCurrentCount() + arrived);
        return new Pair<>(s, arrived);

    }

    @Override
    public Shipment storeShipmentInDb(Shipment s, int count) throws ServiceException {
        Item i = s.getItem();
        i.setCurrentCount(i.getCurrentCount() + count);
        itemService.update(i);
        shipmentService.create(s);
        return s;
    }
}
