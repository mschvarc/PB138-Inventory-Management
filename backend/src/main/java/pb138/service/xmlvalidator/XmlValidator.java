package pb138.service.xmlvalidator;

import java.net.URL;
import pb138.service.exceptions.XmlValidationException;

public interface XmlValidator {

    /**
     * Validate xml against the schema.
     * 
     * @param xmlContent - xml to be validated
     * @param xmlSchema - URL to xml schema, against what should be the xml valid
     * @throws IllegalArgumentException if any argument is null or xml schema is invalid
     * @throws XmlValidationException if there is an error in the xml
     */
    void validate(String xmlContent, URL xmlSchema) throws XmlValidationException;

    /**
     * Check, if xml is valid against the schema.
     * 
     * @param xmlContent - xml to be validated
     * @param xmlSchema - URL to xml schema, against what should be the xml valid
     * @throws IllegalArgumentException if any argument is null or xml schema is invalid
     * 
     * @return true if xml is valid, false otherwise
     */
    boolean isValid(String xmlContent, URL xmlSchema);

}
