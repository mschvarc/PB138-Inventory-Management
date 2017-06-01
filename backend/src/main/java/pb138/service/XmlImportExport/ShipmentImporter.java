package pb138.service.XmlImportExport;

import org.w3c.dom.Element;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.exceptions.ServiceException;

/**
 * For importing shipments
 *
 */
public interface ShipmentImporter {
    /**
     * Imports shipments
     * @param e root element of XML file
     * @throws ServiceException If there are unexpected troubles on service layer
     * @throws EntityDoesNotExistException If one of items referenced in shipments does not exist
     */
    void importShipments(Element e) throws EntityDoesNotExistException, ServiceException;
}
