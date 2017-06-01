package pb138.service.exceptions;

/**
 * This is thrown when we want to sale more that is currently in stock
 */
public class NotEnoughStoredException extends Exception{
    /**
     * Creates new exception with given message
     * @param message message for exception
     */
    public NotEnoughStoredException(String message) {
        super(message);
    }

    /**
     * Creates new exception with given message
     * @param message message for exception
     * @param cause cause of exception
     */
    public NotEnoughStoredException(String message, Throwable cause) {
        super(message, cause);
    }
}
