package pb138.service.facades;

import pb138.dal.entities.Sale;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.exceptions.NotEnoughStoredException;
import pb138.service.exceptions.ServiceException;

import java.util.Date;
import java.util.List;
import pb138.dal.entities.Category;
import pb138.dal.entities.Item;

/**
 * Created by Honza on 30.04.2017.
 * Facade for working with sales
 */
public interface SaleFacade {


    //Must be solved - what if the total amount of sales is greater than current stock?
    Sale addSale(long ean, Date date, int sold) throws EntityDoesNotExistException, NotEnoughStoredException;

    Sale storeSaleInDb(Sale s) throws ServiceException;

    // Pro Marketku, ty to zrejme budes exportovat do xml, nazev kategorie/ ean produktu a data ti prijdou z webove vrstvy,
    // (asi na to Dominikovi nachystej nejaky interface, neco jako xmlExporter) vratim ti to jako list, do XML uz si to pak nejak zpracuj :)

    /**
     * Get all sales for given category from given period of time
     * @param categoryName name of category
     * @param from minimum date for sales
     * @param to maximum date for sales
     * @return list of sales
     * @throws EntityDoesNotExistException if there is no such category
     */
    List<Sale> getSalesForCategory(Category category, Date from, Date to) throws IllegalArgumentException;

    /**
     * Get all sales for given item from given period of time
     * @param ean ean of item
     * @param from minimum date for sales
     * @param to maximum date for sales
     * @return list of sales
     * @throws EntityDoesNotExistException if there is no such item
     */
    List<Sale> getSalesForProduct(Item item, Date from, Date to) throws IllegalArgumentException;

    /**
     * Get all sales
     * @return all sales
     */
    List<Sale> getAllSales();
}
