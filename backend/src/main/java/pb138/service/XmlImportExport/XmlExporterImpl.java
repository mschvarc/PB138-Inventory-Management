package pb138.service.XmlImportExport;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pb138.dal.entities.Item;
import pb138.service.facades.ItemFacade;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.List;

/**
 * Created by Jan on 06.05.2017.
 * Implements XmlExporter
 */
public class XmlExporterImpl implements XmlExporter {
    private ItemFacade itemFacade;

    public XmlExporterImpl(ItemFacade itemFacade) {
        this.itemFacade = itemFacade;
    }

    @Override
    public Document ExportXml() throws ParserConfigurationException {
        List<Item> items = itemFacade.getAllItems();
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("items");
        doc.appendChild(rootElement);
        for (Item i : items) {
            Element itemElement = doc.createElement("item");


            Element nameElement = doc.createElement("name");
            nameElement.appendChild(doc.createTextNode(i.getName()));
            itemElement.appendChild(nameElement);

            Element eanElement = doc.createElement("ean");
            eanElement.appendChild(doc.createTextNode(Integer.toString(i.getEan())));
            itemElement.appendChild(eanElement);

            Element descriptionElement = doc.createElement("description");
            descriptionElement.appendChild(doc.createTextNode(i.getDescription()));
            itemElement.appendChild(descriptionElement);



            Element currentCountElement = doc.createElement("currentCount");
            currentCountElement.appendChild(doc.createTextNode(Integer.toString(i.getCurrentCount())));
            itemElement.appendChild(currentCountElement);

            Element unitElement = doc.createElement("unit");
            unitElement.appendChild(doc.createTextNode(i.getUnit()));
            itemElement.appendChild(unitElement);

            Element categoryElement = doc.createElement("category");
            Element categoryNameElement = doc.createElement("name");
            categoryNameElement.appendChild(doc.createTextNode(i.getCategory().getName()));
            categoryElement.appendChild(categoryNameElement);
            Element categoryDescriptionElement = doc.createElement("description");
            categoryDescriptionElement.appendChild(doc.createTextNode(i.getCategory().getDescription()));
            categoryElement.appendChild(categoryDescriptionElement);
            itemElement.appendChild(categoryElement);

            Integer alertThreshold = i.getAlertThreshold();
            if (alertThreshold != null) {
                Element alertThresholdElement = doc.createElement("alertThreshold");
                alertThresholdElement.appendChild(doc.createTextNode(alertThreshold.toString()));
                itemElement.appendChild(alertThresholdElement);
            }

            rootElement.appendChild(itemElement);
        }
        return doc;

    }
}
