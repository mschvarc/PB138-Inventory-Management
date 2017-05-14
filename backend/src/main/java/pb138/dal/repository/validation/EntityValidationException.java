package pb138.dal.repository.validation;

/**
 * Represents exceptions thrown on constraint violations
 * @author Martin Schvarcbacher
 */
public class EntityValidationException extends Exception {

    /**
     * Represents exceptions thrown on constraint violations
     *
     * @param message message
     */
    public EntityValidationException(String message) {
        super(message);
    }

    /**
     * Represents exceptions thrown on constraint violations
     *
     * @param message message
     * @param cause   parent exception
     */
    public EntityValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
