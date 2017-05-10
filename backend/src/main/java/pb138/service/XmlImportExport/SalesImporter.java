package pb138.service.XmlImportExport;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.exceptions.NotEnoughStoredException;
import pb138.service.exceptions.ServiceException;

/**
 * Created by Jan on 03.05.2017.
 *
 */
public interface SalesImporter {
    void importSales(Element e) throws ServiceException, EntityDoesNotExistException, NotEnoughStoredException;
}
