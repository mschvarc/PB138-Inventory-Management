package pb138.service.XmlImportExport;

import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.exceptions.NotEnoughStoredException;
import pb138.service.exceptions.ServiceException;
import pb138.service.exceptions.XmlValidationException;

/**
 * Class for importing XML
 *
 */
public interface XmlImporter {

    /**
     * Imports xml file
     * @param xmlFile xml file to import
     * @throws XmlValidationException if something goes wrong during validation
     * @throws EntityDoesNotExistException if some entity doesn't exist
     * @throws NotEnoughStoredException if there is not enough of certain item stored
     * @throws ServiceException if something unexpected goes wrong while storing in db
     */
    void importXml(String xmlFile) throws XmlValidationException, EntityDoesNotExistException, NotEnoughStoredException, ServiceException;

}
