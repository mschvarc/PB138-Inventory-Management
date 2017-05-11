package pb138.service.XmlImportExport;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import pb138.service.exceptions.ServiceException;
import pb138.service.exceptions.XmlValidationException;
import pb138.service.facades.CategoryFacade;

/**
 * Created by Jan on 10.05.2017.
 * Implements category importer
 */
public class CategoryImporterImpl implements CategoryImporter{

    private CategoryFacade categoryFacade;

    public CategoryImporterImpl(CategoryFacade categoryFacade) {
        this.categoryFacade = categoryFacade;
    }

    @Override
    public void importCategories(Element e) throws XmlValidationException, ServiceException {
        NodeList categories = e.getElementsByTagName("category");
        for (int i = 0; i < categories.getLength(); i++) {
            Element category = (Element) categories.item(i);

            NodeList nameNode = category.getElementsByTagName("name");
            String name = nameNode.item(0).getTextContent();

            NodeList descNode = category.getElementsByTagName("description");
            String description = descNode.item(0).getTextContent();
            categoryFacade.createOrUpdateCategory(name, description);


        }
    }
}
