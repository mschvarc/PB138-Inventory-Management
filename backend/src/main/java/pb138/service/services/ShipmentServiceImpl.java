package pb138.service.services;

import com.google.common.collect.Lists;
import pb138.dal.entities.Shipment;
import pb138.dal.repository.ShipmentRepository;
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.exceptions.ServiceException;
import pb138.service.filters.ShipmentFilter;

import java.util.List;

/**
 * Created by Honza on 30.04.2017.
 *
 */
public class ShipmentServiceImpl implements ShipmentService{
    private ShipmentRepository shipmentRepository;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public long create(Shipment shipment) throws ServiceException {
        try {
            shipmentRepository.create(shipment);
            return shipment.getId();
        } catch (EntityValidationException e) {
            throw new ServiceException("Problem with validating the shipment", e);
        }
    }

    @Override
    public void delete(Shipment shipment) throws ServiceException {
        try {
            shipmentRepository.delete(shipment);
        } catch (EntityValidationException e) {
            throw new ServiceException("Problem with validating the shipment", e);
        }
    }

    @Override
    public void update(Shipment shipment) throws ServiceException {
        try {
            shipmentRepository.update(shipment);
        } catch (EntityValidationException e) {
            throw new ServiceException("Problem with validating the shipment", e);
        }
    }

    @Override
    public Shipment getById(long id) {
        return shipmentRepository.getById(id);
    }

    @Override
    public List<Shipment> getAllShipments() {
        ShipmentFilter filter = new ShipmentFilter();
        Iterable<Shipment> result = shipmentRepository.find(filter);
        return Lists.newArrayList(result);
    }
}
