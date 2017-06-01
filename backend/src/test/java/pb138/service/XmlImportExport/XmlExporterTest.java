package pb138.service.XmlImportExport;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import pb138.dal.entities.Item;
import pb138.service.facades.CategoryFacade;
import pb138.service.facades.CreateOrUpdate;
import pb138.service.facades.ItemFacade;
import pb138.utils.Pair;

import javax.transaction.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;



/**
 * Created by Jan on 07.05.2017.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/persistence-config.xml")
@Transactional
public class XmlExporterTest extends TestCase {

    @Autowired
    private CategoryFacade categoryFacade;

    @Autowired
    private ItemFacade itemFacade;

    @Autowired
    private XmlExporter xmlExporter;

    @Before
    public void beforeTests() throws Exception {

    }

    @Test
    public void exportCorrectly() throws Exception {
        categoryFacade.createOrUpdateCategory("Electronics", "Computers, TVs, etc");
        categoryFacade.createOrUpdateCategory("Clothes", "T-shirts and pants");
        Pair<Item, CreateOrUpdate> i1 = itemFacade.createOrUpdateItem("PC", "Awesome gaming computer", "Electronics", null, "pieces", 213);
        itemFacade.storeItemInDb(i1);

        Pair<Item, CreateOrUpdate> i2 = itemFacade.createOrUpdateItem("Notebook", "Apple iBook", "Electronics", null, "pieces", 35);
        itemFacade.storeItemInDb(i2);

        Pair<Item, CreateOrUpdate> i3 = itemFacade.createOrUpdateItem("PC", "Awesome gaming computer", "Electronics", null, "pieces", 2013);
        itemFacade.storeItemInDb(i3);

        Pair<Item, CreateOrUpdate> i4 = itemFacade.createOrUpdateItem("t-shirt", "Nice blue T-Shirt", "Clothes", null, "pieces", 21312);
        itemFacade.storeItemInDb(i4);
        String s = xmlExporter.exportXmlToString();
        Document doc = xmlExporter.exportXmlToDoc();
        Element root = doc.getDocumentElement();
        assertThat(root.getTagName(), is("items"));
        NodeList itemList = root.getChildNodes();
        assertThat(itemList.getLength(), is(4));
    }

    @Test
    public void exportEmpty() throws Exception {
        String s = xmlExporter.exportXmlToString();
        assertThat(s.contains("<items/>"), is(true));
    }
}
