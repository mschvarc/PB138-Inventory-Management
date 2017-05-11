package pb138.service.XmlImportExport;

import org.w3c.dom.Element;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.exceptions.ServiceException;

/**
 * Created by Jan on 08.05.2017.
 *
 */
public interface ItemImporter {
    void importItems(Element e) throws ServiceException, EntityDoesNotExistException;
}
