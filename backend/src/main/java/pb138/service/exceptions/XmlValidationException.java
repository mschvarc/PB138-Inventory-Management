package pb138.service.exceptions;

/**
 * Exception for cases when there are problems with XML validation
 */
public class XmlValidationException extends Exception {
    /**
     * Creates new exception with given message
     * @param message message for exception
     */
    public XmlValidationException(String message) {
        super(message);
    }

    /**
     * Creates new exception with given message
     * @param message message for exception
     * @param cause cause of exception
     */
    public XmlValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates new exception with given message
     * @param cause cause of exception
     */
    public XmlValidationException(Throwable cause) {
        super(cause);
    }
}
