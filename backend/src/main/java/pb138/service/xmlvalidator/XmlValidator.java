package pb138.service.xmlvalidator;

import pb138.service.exceptions.XmlValidationException;

public interface XmlValidator {

    void validate(String xmlContent, String xmlSchema) throws XmlValidationException;

    boolean isValid(String xmlContent, String xmlSchema);

}
