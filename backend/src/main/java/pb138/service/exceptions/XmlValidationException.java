package pb138.service.exceptions;

public class XmlValidationException extends Exception {
    public XmlValidationException(String message) {
        super(message);
    }

    public XmlValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlValidationException(Throwable cause) {
        super(cause);
    }
}
