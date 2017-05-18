package pb138.service.XmlImportExport;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.exceptions.NotEnoughStoredException;
import pb138.service.exceptions.ServiceException;
import pb138.service.exceptions.XmlValidationException;
import pb138.service.xmlvalidator.XmlValidator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Jan on 06.05.2017.
 * Implements XmlImporter
 */
public class XmlImporterImpl implements XmlImporter{

    private XmlValidator xmlValidator;

    private ShipmentImporter shipmentImporter;

    private CategoryImporter categoryImporter;

    private ItemImporter itemImporter;

    private SalesImporter salesImporter;

    public XmlImporterImpl(XmlValidator xmlValidator, ShipmentImporter shipmentImporter,
                           CategoryImporter categoryImporter, ItemImporter itemImporter, SalesImporter salesImporter) {
        this.xmlValidator = xmlValidator;
        this.shipmentImporter = shipmentImporter;
        this.categoryImporter = categoryImporter;
        this.itemImporter = itemImporter;
        this.salesImporter = salesImporter;
    }

    @Override
    public void importXml(String xmlFile) throws XmlValidationException, EntityDoesNotExistException, NotEnoughStoredException, ServiceException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlFile)));
            Element root = document.getDocumentElement();
            ClassLoader classLoader = getClass().getClassLoader();
            if (root == null) {
                throw new XmlValidationException("Xml has no root");
            }
            switch (root.getTagName()){
                case "categories":
                    xmlValidator.validate(xmlFile, classLoader.getResource("xml_schema/categories_xml_schema.xsd"));
                    categoryImporter.importCategories(root);
                    break;
                case "items":
                    xmlValidator.validate(xmlFile, classLoader.getResource("xml_schema/items_xml_schema.xsd") );
                    itemImporter.importItems(root);
                    break;
                case "sales":
                    xmlValidator.validate(xmlFile, classLoader.getResource("xml_schema/sales_xml_schema.xsd"));
                    salesImporter.importSales(root);
                    break;
                case "shipments":
                    xmlValidator.validate(xmlFile, classLoader.getResource("xml_schema/shipments_xml_schema.xsd"));
                    shipmentImporter.importShipments(root);
                    break;
                default:
                    throw new XmlValidationException("Root is different than it should be");


            }
        } catch (ParserConfigurationException|SAXException|IOException e ) {
            throw new XmlValidationException("Unable to parse XML", e);
        }
        //throw new NotImplementedException();
    }
}
