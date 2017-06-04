package pb138.service.xmlvalidator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;
import pb138.service.exceptions.XmlValidationException;

/**
 * Class for validating xml against the xml schema.
 * Implementation of XmlValidator interface.
 *
 * @author Marketa Elederova
 */
public class XmlValidatorImpl implements XmlValidator {

    @Override
    public void validate(String xmlContent, URL xmlSchema) throws XmlValidationException {

        if (xmlContent == null) {
            throw new IllegalArgumentException("xmlContent is null");
        }
        if (xmlSchema == null) {
            throw new IllegalArgumentException("xmlSchema is null");
        }

        Schema schema;
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            schema = schemaFactory.newSchema(xmlSchema);
        } catch (SAXException e) {
            throw new IllegalArgumentException("Invalid schema " + e.getMessage(), e);
        }

        Validator validator = schema.newValidator();
        StreamSource xmlStreamSource = new StreamSource(new ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.UTF_8)));
        try {
            validator.validate(xmlStreamSource);
        } catch (SAXException | IOException e) {
            throw new XmlValidationException("Invalid xml " + e.getMessage(), e);
        }
    }

    @Override
    public boolean isValid(String xmlContent, URL xmlSchema) {

        try {
            validate(xmlContent, xmlSchema);
            return true;
        } catch (XmlValidationException e) {
            return false;
        }
    }

}
