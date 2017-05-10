package pb138.service.XmlImportExport;

import org.w3c.dom.Element;
import pb138.service.exceptions.ServiceException;
import pb138.service.exceptions.XmlValidationException;
import pb138.service.xmlvalidator.XmlValidator;

/**
 * Created by Jan on 08.05.2017.
 *
 */
public interface CategoryImporter {
    void importCategories(Element e) throws XmlValidationException, ServiceException;
}
