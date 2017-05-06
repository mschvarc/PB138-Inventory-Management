package pb138.service.facades;

import pb138.dal.entities.Sale;
import pb138.service.exceptions.EntityDoesNotExistException;

import java.util.Date;
import java.util.List;

/**
 * Created by Honza on 30.04.2017.
 * Facade for working with sales
 */
public interface SaleFacade {


    //Must be solved - what if the total amount of sales is greater than current stock?
    Sale addSale(int ean, Date date, int sold);

    Sale storeSaleInDb(Sale s);

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
    List<Sale> getSalesForCategory(String categoryName, Date from, Date to) throws EntityDoesNotExistException;

    /**
     * Get all sales for given item from given period of time
     * @param ean ean of item
     * @param from minimum date for sales
     * @param to maximum date for sales
     * @return list of sales
     * @throws EntityDoesNotExistException if there is no such item
     */
    List<Sale> getSalesForProduct(int ean, Date from, Date to) throws EntityDoesNotExistException;
}
