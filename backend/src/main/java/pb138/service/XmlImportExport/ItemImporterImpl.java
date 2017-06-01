package pb138.service.XmlImportExport;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import pb138.dal.entities.Item;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.exceptions.ServiceException;
import pb138.service.facades.CreateOrUpdate;
import pb138.service.facades.ItemFacade;
import pb138.utils.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jan on 10.05.2017.
 *
 */
public class ItemImporterImpl implements ItemImporter{
    private ItemFacade itemFacade;

    /**
     * Constructor
     * @param itemFacade item facade
     */
    public ItemImporterImpl(ItemFacade itemFacade) {
        this.itemFacade = itemFacade;
    }

    @Override
    public void importItems(Element e) throws ServiceException, EntityDoesNotExistException {
        NodeList shipments = e.getElementsByTagName("item");
        List<Pair<Item, CreateOrUpdate>> items = new ArrayList<>();
        for (int i = 0; i < shipments.getLength(); i++) {
            Element item = (Element) shipments.item(i);

            NodeList eanNode = item.getElementsByTagName("ean");
            String eanString = eanNode.item(0).getTextContent();
            long ean = Long.parseLong(eanString);

            NodeList nameNode = item.getElementsByTagName("name");
            String name = nameNode.item(0).getTextContent();

            NodeList unitNode = item.getElementsByTagName("unit");
            String unit = unitNode.item(0).getTextContent();

            NodeList descNode = item.getElementsByTagName("description");
            String description = descNode.item(0).getTextContent();

            NodeList categoryNameNode = item.getElementsByTagName("categoryName");
            String categoryName = categoryNameNode.item(0).getTextContent();

            NodeList alertThresholdNode = item.getElementsByTagName("threshold");
            Integer alertThreshold = null;
            if (alertThresholdNode.getLength() == 1) {
                String thresholdString = alertThresholdNode.item(0).getTextContent();
                alertThreshold = Integer.parseInt(thresholdString);
            }

            items.add(itemFacade.createOrUpdateItem(name, description, categoryName, alertThreshold, unit, ean));

        }
        for (Pair<Item, CreateOrUpdate> item : items) {
            itemFacade.storeItemInDb(item);
        }
    }
}
