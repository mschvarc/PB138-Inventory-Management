package pb138.service.XmlImportExport;

import org.w3c.dom.Document;
import pb138.service.exceptions.XmlValidationException;

import javax.xml.parsers.ParserConfigurationException;

/**
 * For exporting XML of current stock
 *
 */
public interface XmlExporter {

    /**
     * Exports all items in database to xml file
     * @return xml in Document format (may be changed)
     * @throws ParserConfigurationException I have no idea why this could happen
     */
    Document exportXmlToDoc() throws ParserConfigurationException;

    /**
     * Exports all items in database to string with XML
     * @return string containing all items in XML format
     * @throws XmlValidationException if there are problems while validating XML
     */
    String exportXmlToString() throws XmlValidationException;
}
