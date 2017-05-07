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
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


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




    @Override
    public boolean importXml(String xmlFile) throws XmlValidationException, EntityDoesNotExistException, NotEnoughStoredException, ServiceException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlFile)));
            Element root = document.getDocumentElement();
            if (root == null) {
                throw new XmlValidationException("Xml has no root");
            }
            switch (root.getTagName()){
                case "categories":
                    xmlValidator.validate(xmlFile, null /*categories scheme here*/);
                    break;
                case "items":
                    xmlValidator.validate(xmlFile, null /*items scheme here*/);
                    break;
                case "sales":
                    xmlValidator.validate(xmlFile, null /*sales scheme here*/);
                    break;
                case "shipments":
                    xmlValidator.validate(xmlFile, null /*shipments scheme here*/);
                    break;
                default:
                    throw new XmlValidationException("Root is different than it should be");


            }
        } catch (ParserConfigurationException|SAXException|IOException e ) {
            throw new XmlValidationException("Unable to parse XML", e);
        }
        throw new NotImplementedException();
    }
}
