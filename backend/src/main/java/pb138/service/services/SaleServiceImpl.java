package pb138.service.services;

import pb138.dal.entities.Sale;
import pb138.dal.repository.SaleRepository;
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.exceptions.ServiceException;

/**
 * Created by Honza on 30.04.2017.
 *
 */
public class SaleServiceImpl implements SaleService{
    private SaleRepository saleRepository;

    public SaleServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public long create(Sale sale) throws ServiceException {
        try {
            saleRepository.create(sale);
            return sale.getId();
        } catch (EntityValidationException e) {
            throw new ServiceException("Problem with validating the sale", e);
        }
    }

    @Override
    public void delete(Sale sale) throws ServiceException {
        try {
            saleRepository.delete(sale);
        } catch (EntityValidationException e) {
            throw new ServiceException("Problem with validating the sale", e);
        }
    }

    @Override
    public void update(Sale sale) throws ServiceException {
        try {
            saleRepository.update(sale);
        } catch (EntityValidationException e) {
            throw new ServiceException("Problem with validating the sale", e);
        }
    }

    @Override
    public Sale getById(long id) {
        return saleRepository.getById(id);
    }
}
