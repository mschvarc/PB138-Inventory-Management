package pb138.service.XmlImportExport;

import org.w3c.dom.Element;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.exceptions.ServiceException;

/**
 * Created by Jan on 08.05.2017.
 *
 */
public interface ShipmentImporter {
    void importShipments(Element e) throws EntityDoesNotExistException, ServiceException;
}
