package pb138.service.XmlImportExport;

import org.w3c.dom.Element;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.exceptions.ServiceException;

/**
 * For importing items
 *
 */
public interface ItemImporter {
    /**
     * Imports items
     * @param e root element of XML file
     * @throws ServiceException If there are unexpected troubles on service layer
     * @throws EntityDoesNotExistException If one of categories referenced in items does not exist
     */
    void importItems(Element e) throws ServiceException, EntityDoesNotExistException;
}
