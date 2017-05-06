package pb138.service.XmlImportExport;

import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.exceptions.NotEnoughStoredException;
import pb138.service.exceptions.ServiceException;
import pb138.service.exceptions.XmlValidationException;

/**
 * Created by Jan on 03.05.2017.
 *
 */
public interface XmlImporter {

    /**
     * Imports xml file
     * @param xmlFile to import
     * @return true if import was succesful, false if xml file is not valid (if something happens during importing, eg. with db,
     * exception will be thrown)
     */
    boolean importXml(String xmlFile) throws XmlValidationException, EntityDoesNotExistException, NotEnoughStoredException, ServiceException;

}
