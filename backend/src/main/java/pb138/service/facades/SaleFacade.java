package pb138.service.facades;

import pb138.dal.entities.Category;
import pb138.dal.entities.Item;
import pb138.dal.entities.Sale;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.exceptions.NotEnoughStoredException;
import pb138.service.exceptions.ServiceException;

import java.util.Date;
import java.util.List;

/**
 * Facade for working with sales
 */
public interface SaleFacade {


    /**
     * Adds one sale
     * @param ean EAN of item
     * @param date date of sale
     * @param sold items sold
     * @return Sale that was added
     * @throws EntityDoesNotExistException if item with this EAN doesn't exist
     * @throws NotEnoughStoredException if there is not enough items of this item stored
     */
    Sale addSale(long ean, Date date, int sold) throws EntityDoesNotExistException, NotEnoughStoredException;

    /**
     * Store sale in db.
     *
     * @param s sale to store
     * @return the sale
     * @throws ServiceException if sale is invalid or some error occurs
     */
    Sale storeSaleInDb(Sale s) throws ServiceException;

    /**
     * Get all sales for given category from given period of time
     * @param category category
     * @param from minimum date for sales
     * @param to maximum date for sales
     * @return list of sales
     * @throws IllegalArgumentException if category is null
     */
    List<Sale> getSalesForCategory(Category category, Date from, Date to) throws IllegalArgumentException;

    /**
     * Get all sales for given item from given period of time
     * @param item item
     * @param from minimum date for sales
     * @param to maximum date for sales
     * @return list of sales
     * @throws IllegalArgumentException item is null
     */
    List<Sale> getSalesForProduct(Item item, Date from, Date to) throws IllegalArgumentException;

    /**
     * Get all sales
     * @return all sales
     */
    List<Sale> getAllSales();
}
