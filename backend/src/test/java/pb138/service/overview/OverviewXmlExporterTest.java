package pb138.service.overview;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import pb138.dal.entities.Category;
import pb138.dal.entities.Item;
import pb138.service.exceptions.XmlValidationException;

/**
 * Tests for OverviewXmlExportImpl class.
 *
 * @author Marketa Elederova
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/persistence-config.xml")
@Transactional
public class OverviewXmlExporterTest extends TestCase {

    @Autowired
    private OverviewXmlExporter overviewXmlExporter;

    @Test
    public void exportEmptyOverview() throws XmlValidationException, ParserConfigurationException, SAXException, IOException {

        String xml = overviewXmlExporter.exportCategoryResultToXml(new ArrayList<>());
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputStream is = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        Document doc = builder.parse(is);

        Element root = doc.getDocumentElement();
        assertEquals("categoryOverview", root.getTagName());
        assertEquals(0, root.getChildNodes().getLength());
    }

    @Test
    public void exportItemOverview()
            throws XmlValidationException, ParserConfigurationException, SAXException, IOException {

        Item item = new Item();
        item.setEan(7614500010013l);
        item.setName("Toblerone Milk Chocolate");
        item.setUnit("packet");
        item.setDescription("Milk chocolate.");
        Category category = new Category();
        category.setName("sweet-stuff");
        item.setCategory(category);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, 0, 30);
        OverviewResultItem ori1 = new OverviewResultItem(Period.ofWeeks(1), calendar.getTime(), item, 3);
        calendar.set(2017, 1, 6);
        OverviewResultItem ori2 = new OverviewResultItem(Period.ofWeeks(1), calendar.getTime(), item, 4);

        List<OverviewResultItem> results = new ArrayList<>();
        results.add(ori1);
        results.add(ori2);

        String xml = overviewXmlExporter.exportItemResultToXml(results);
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputStream is = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        Document doc = builder.parse(is);

        Element root = doc.getDocumentElement();
        assertEquals("itemOverview", root.getTagName());

        NodeList overviewNodes = root.getElementsByTagName("overview");
        assertEquals(2, overviewNodes.getLength());

        NodeList nodes = overviewNodes.item(0).getChildNodes();
        assertEquals(9, nodes.getLength());     //between elements are text nodes
        assertEquals("timespan", nodes.item(1).getNodeName());
        assertEquals("week", nodes.item(1).getTextContent());
        assertEquals("dateStart", nodes.item(3).getNodeName());
        assertEquals("2017-01-30", nodes.item(3).getTextContent());
        assertEquals("item", nodes.item(5).getNodeName());
        assertEquals("countSold", nodes.item(7).getNodeName());
        assertEquals("3", nodes.item(7).getTextContent());

        NodeList itemNodes = nodes.item(5).getChildNodes();
        assertEquals(11, itemNodes.getLength());     //between elements are text nodes
        assertEquals("ean", itemNodes.item(1).getNodeName());
        assertEquals("7614500010013", itemNodes.item(1).getTextContent());
        assertEquals("name", itemNodes.item(3).getNodeName());
        assertEquals("Toblerone Milk Chocolate", itemNodes.item(3).getTextContent());
        assertEquals("unit", itemNodes.item(5).getNodeName());
        assertEquals("packet", itemNodes.item(5).getTextContent());
        assertEquals("description", itemNodes.item(7).getNodeName());
        assertEquals("Milk chocolate.", itemNodes.item(7).getTextContent());
        assertEquals("categoryName", itemNodes.item(9).getNodeName());
        assertEquals("sweet-stuff", itemNodes.item(9).getTextContent());
    }

    @Test
    public void exportCategoryOverview()
            throws XmlValidationException, ParserConfigurationException, SAXException, IOException {

        Category category = new Category();
        category.setName("sweet-stuff");
        category.setDescription("Foodstuffs: chocolate, candies etc.");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, 0, 30);
        OverviewResultCategory orc1 = new OverviewResultCategory(Period.ofWeeks(1), calendar.getTime(), category, 20);
        calendar.set(2017, 1, 6);
        OverviewResultCategory orc2 = new OverviewResultCategory(Period.ofWeeks(1), calendar.getTime(), category, 30);

        List<OverviewResultCategory> results = new ArrayList<>();
        results.add(orc1);
        results.add(orc2);

        String xml = overviewXmlExporter.exportCategoryResultToXml(results);
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputStream is = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        Document doc = builder.parse(is);

        Element root = doc.getDocumentElement();
        assertEquals("categoryOverview", root.getTagName());

        NodeList overviewNodes = root.getElementsByTagName("overview");
        assertEquals(2, overviewNodes.getLength());

        NodeList nodes = overviewNodes.item(0).getChildNodes();
        assertEquals(9, nodes.getLength());     //between elements are text nodes
        assertEquals("timespan", nodes.item(1).getNodeName());
        assertEquals("week", nodes.item(1).getTextContent());
        assertEquals("dateStart", nodes.item(3).getNodeName());
        assertEquals("2017-01-30", nodes.item(3).getTextContent());
        assertEquals("category", nodes.item(5).getNodeName());
        assertEquals("countSold", nodes.item(7).getNodeName());
        assertEquals("20", nodes.item(7).getTextContent());

        NodeList categoryNodes = nodes.item(5).getChildNodes();
        assertEquals(5, categoryNodes.getLength());     //between elements are text nodes
        assertEquals("name", categoryNodes.item(1).getNodeName());
        assertEquals("sweet-stuff", categoryNodes.item(1).getTextContent());
        assertEquals("description", categoryNodes.item(3).getNodeName());
        assertEquals("Foodstuffs: chocolate, candies etc.", categoryNodes.item(3).getTextContent());
    }
}
