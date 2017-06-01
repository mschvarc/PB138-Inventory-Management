package pb138.service.services;

import pb138.dal.entities.Sale;
import pb138.service.exceptions.ServiceException;
import pb138.service.filters.SaleFilter;

import java.util.List;

/**
 * Class for working with sales
 */
public interface SaleService {
    /**
     * Creates new sale
     * @param sale Sale to be created
     * @return id of created sale
     * @throws ServiceException when there are any unexpected problems
     */
    long create(Sale sale) throws ServiceException;

    /**
     * Deletes sale
     * @param sale Sale to be deleted
     * @throws ServiceException when there are any unexpected problems
     */
    void delete(Sale sale) throws ServiceException;

    /**
     * Updates sale
     * @param sale Sale to be updated
     * @throws ServiceException when there are any unexpected problems
     */
    void update(Sale sale) throws ServiceException;

    /**
     * Return sale of given id
     * @param id Id of sale
     * @return sale with this id, NULL if not present
     */
    Sale getById(long id);

    /**
     * Returns sales based on given filter
     * @param filter filter to use
     * @return filtered sales
     */
    List<Sale> getByFilter(SaleFilter filter);
}
