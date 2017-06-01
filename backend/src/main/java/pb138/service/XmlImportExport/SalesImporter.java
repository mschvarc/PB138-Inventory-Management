package pb138.service.XmlImportExport;

import org.w3c.dom.Element;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.exceptions.NotEnoughStoredException;
import pb138.service.exceptions.ServiceException;

/**
 * For importing sales
 */
public interface SalesImporter {
    /**
     * Imports sales
     * @param e root element of XML file
     * @throws ServiceException If there are unexpected troubles on service layer
     * @throws EntityDoesNotExistException If one of items referenced in sales does not exist
     * @throws NotEnoughStoredException if there is not enough items stored
     */
    void importSales(Element e) throws ServiceException, EntityDoesNotExistException, NotEnoughStoredException;
}
