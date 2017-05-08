package pb138.service.XmlImportExport;
import org.jdom2.Element;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import org.jdom2.*;

/**
 * Created by Jan on 03.05.2017.
 *
 */
public interface SalesImporter {
    void importSales(Element e);
}
