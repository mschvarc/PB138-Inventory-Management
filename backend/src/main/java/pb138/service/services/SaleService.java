package pb138.service.services;

import pb138.dal.entities.Sale;
import pb138.service.exceptions.ServiceException;


public interface SaleService {
    long create(Sale sale) throws ServiceException;

    void delete(Sale sale) throws ServiceException;

    void update(Sale sale) throws ServiceException;

    Sale getById(long id);
}
