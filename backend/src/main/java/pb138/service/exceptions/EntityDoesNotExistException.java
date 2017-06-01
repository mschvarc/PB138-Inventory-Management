package pb138.service.exceptions;

/**
 * Exception for cases when referenced item doesn't exist
 */
public class EntityDoesNotExistException extends Exception {
    /**
     * Creates new exception with given message
     * @param message message for exception
     */
    public EntityDoesNotExistException(String message) {
        super(message);
    }

    /**
     * Creates new exception with given message
     * @param message message for exception
     * @param cause cause of exception
     */
    public EntityDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
