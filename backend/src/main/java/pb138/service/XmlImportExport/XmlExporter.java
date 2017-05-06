package pb138.service.XmlImportExport;

import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Jan on 06.05.2017.
 *
 */
public interface XmlExporter {

    /**
     * Exports all items in database to xml file
     * @return xml in Document format (may be changed)
     * @throws ParserConfigurationException I have no idea why could this happen
     */
    Document ExportXml() throws ParserConfigurationException;
}
