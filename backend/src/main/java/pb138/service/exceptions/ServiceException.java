package pb138.service.exceptions;

/**
 * Exception for cases when there are problems on service layer
 */
public class ServiceException extends Exception {
    /**
     * Creates new exception with given message
     * @param message message for exception
     * @param cause cause of exception
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates new exception with given message
     * @param message message for exception
     */
    public ServiceException(String message) {
        super(message);
    }
}
