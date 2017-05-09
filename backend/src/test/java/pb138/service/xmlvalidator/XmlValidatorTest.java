package pb138.service.xmlvalidator;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.Before;
import org.junit.Test;
import pb138.service.exceptions.XmlValidationException;

/**
 * Tests for XmlValidator.
 *
 * @author Marketa Elederova
 */
public class XmlValidatorTest {

    private XmlValidator xmlValidator;
    private URL xmlSchemaShipments;
    private URL xmlSchemaItems;
    private URL xmlSchemaSales;
    private URL xmlSchemaCategories;

    @Before
    public void beforeTest() throws MalformedURLException {
        xmlValidator = new XmlValidatorImpl();
        xmlSchemaShipments = getClass().getClassLoader().getResource("xml_schema/shipments_xml_schema.xsd");
        xmlSchemaItems = getClass().getClassLoader().getResource("xml_schema/items_xml_schema.xsd");
        xmlSchemaSales = getClass().getClassLoader().getResource("xml_schema/sales_xml_schema.xsd");
        xmlSchemaCategories = getClass().getClassLoader().getResource("xml_schema/categories_xml_schema.xsd");
    }

    private String getTextContent(String resource) throws IOException, URISyntaxException {
        return new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource(resource).toURI())), "UTF-8");
    }

    @Test
    public void validateValidShipments() throws IOException, URISyntaxException, XmlValidationException {
        String testXml = getTextContent("xml_schema/examples/example_shipments.xml");
        xmlValidator.validate(testXml, xmlSchemaShipments);
        assertTrue(xmlValidator.isValid(testXml, xmlSchemaShipments));
    }

    @Test
    public void validateValidItems() throws IOException, URISyntaxException, XmlValidationException {
        String testXml = getTextContent("xml_schema/examples/example_items.xml");
        xmlValidator.validate(testXml, xmlSchemaItems);
        assertTrue(xmlValidator.isValid(testXml, xmlSchemaItems));
    }

    @Test
    public void validateValidSales() throws IOException, URISyntaxException, XmlValidationException {
        String testXml = getTextContent("xml_schema/examples/example_sales.xml");
        xmlValidator.validate(testXml, xmlSchemaSales);
        assertTrue(xmlValidator.isValid(testXml, xmlSchemaSales));
    }

    @Test
    public void validateValidCategories() throws IOException, URISyntaxException, XmlValidationException {
        String testXml = getTextContent("xml_schema/examples/example_categories.xml");
        xmlValidator.validate(testXml, xmlSchemaCategories);
        assertTrue(xmlValidator.isValid(testXml, xmlSchemaCategories));
    }

    @Test
    public void validateNotXml() {
        assertThatThrownBy(() -> xmlValidator.validate("something what is not xml", xmlSchemaItems))
                .isInstanceOf(XmlValidationException.class);
    }

    @Test
    public void validateWithInvalidSchema() throws IOException, URISyntaxException {
        String testXml = getTextContent("xml_schema/examples/example_sales.xml");
        assertThatThrownBy(() -> xmlValidator.validate(testXml, getClass().getClassLoader().getResource("xml_schema/examples/example_sales.xml")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void validateInvalidXml() throws IOException, URISyntaxException {
        String testXml = getTextContent("xml_schema/examples/example_sales.xml");
        assertThatThrownBy(() -> xmlValidator.validate(testXml, xmlSchemaItems))
                .isInstanceOf(XmlValidationException.class);
    }
}
