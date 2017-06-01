package pb138.service.XmlImportExport;

import org.w3c.dom.Element;
import pb138.service.exceptions.ServiceException;
import pb138.service.exceptions.XmlValidationException;

/**
 * For importing categories
 *
 */
public interface CategoryImporter {
    /**
     * Imports categoires
     * @param e root element of XML file
     * @throws XmlValidationException If XML cannot be validated
     * @throws ServiceException If there are unexpected troubles on service layer
     */
    void importCategories(Element e) throws XmlValidationException, ServiceException;
}
