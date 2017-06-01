package pb138.service.exceptions;

/**
 * Exception for cases when item with the same business key already exists
 *
 */
public class EntityAlreadyExistsException extends Exception {
    /**
     * Creates new exception with given message
     * @param message message for exception
     */
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
