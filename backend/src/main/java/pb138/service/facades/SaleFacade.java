package pb138.service.facades;

import pb138.dal.entities.Sale;

import java.util.Date;
import java.util.List;

/**
 * Created by Honza on 30.04.2017.
 * Facade for working with sales
 */
public interface SaleFacade {
    Sale addSale(int ean, Date date, int sold);

    // Pro Marketku, ty to zrejme budes exportovat do xml, nazev kategorie/ ean produktu a data ti prijdou z webove vrstvy,
    // (asi na to Dominikovi nachystej nejaky interface, neco jako xmlExporter) vratim ti to jako list, do XML uz si to pak nejak zpracuj :)
    List<Sale> getSalesForCategory(String categoryName, Date from, Date to);

    List<Sale> getSalesForProduct(int ean, Date from, Date to);
}
