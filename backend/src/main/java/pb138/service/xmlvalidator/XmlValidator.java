package pb138.service.xmlvalidator;

import pb138.service.exceptions.XmlValidationException;

public interface XmlValidator {

    /**
     * Validate xml against the schema.
     * 
     * @param xmlContent - xml to be validated
     * @param xmlSchemaPath - path to xml schema, against what should be the xml valid
     * @throws XmlValidationException when there is an error in the xml
     */
    void validate(String xmlContent, String xmlSchemaPath) throws XmlValidationException;

    /**
     * Check, if xml is valid against the schema.
     * 
     * @param xmlContent - xml to be validated
     * @param xmlSchemaPath - path to xml schema, against what should be the xml valid
     * @return true, if xml is valid, false otherwise
     */
    boolean isValid(String xmlContent, String xmlSchemaPath);

}
