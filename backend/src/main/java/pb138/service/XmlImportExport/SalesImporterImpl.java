package pb138.service.XmlImportExport;


import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import pb138.dal.entities.Item;
import pb138.dal.entities.Sale;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.exceptions.NotEnoughStoredException;
import pb138.service.exceptions.ServiceException;
import pb138.service.facades.SaleFacade;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jan on 10.05.2017.
 *
 */
public class SalesImporterImpl implements SalesImporter{
    private SaleFacade saleFacade;

    /**
     * Constructor
     * @param saleFacade sale facade
     */
    public SalesImporterImpl(SaleFacade saleFacade) {
        this.saleFacade = saleFacade;

    }

    @Override
    public void importSales(Element e) throws ServiceException, EntityDoesNotExistException, NotEnoughStoredException {
        NodeList sales = e.getElementsByTagName("sale");
        List<Sale> salesList = new ArrayList<>();
        for (int i = 0; i < sales.getLength(); i++) {
            Element sale = (Element) sales.item(i);

            NodeList eanNode = sale.getElementsByTagName("itemEan");
            String eanString = eanNode.item(0).getTextContent();
            long ean = Long.parseLong(eanString);

            NodeList amountNode = sale.getElementsByTagName("amount");
            String amountString = amountNode.item(0).getTextContent();
            int amount = Integer.parseInt(amountString);

            NodeList dateNode = sale.getElementsByTagName("date");
            String dateString = dateNode.item(0).getTextContent();
            String[] parsedDate = dateString.split("-");
            Date date = new GregorianCalendar(Integer.parseInt(parsedDate[0]), Integer.parseInt(parsedDate[1]), Integer.parseInt(parsedDate[2])).getTime();
            salesList.add(saleFacade.addSale(ean, date, amount));
        }
        Map<Item, Integer> itemWithSales = new HashMap<>();
        for (Sale s : salesList) {
            Item i = s.getItem();
            if (itemWithSales.containsKey(i)) {
                Integer sale = itemWithSales.get(i);
                itemWithSales.put(i, sale + s.getQuantitySold());
            } else {
                itemWithSales.put(i, s.getQuantitySold());
            }
        }
        for (Map.Entry<Item, Integer> i : itemWithSales.entrySet()) {
            if (i.getKey().getCurrentCount() < i.getValue()) {
                throw new NotEnoughStoredException("There is not enough of " + i.getKey().getName() + " stored");
            }
        }
        for (Sale s : salesList) {
            saleFacade.storeSaleInDb(s);
        }

    }
}
