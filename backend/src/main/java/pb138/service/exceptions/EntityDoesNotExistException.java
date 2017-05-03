package pb138.service.exceptions;

/**
 * Created by Jan on 03.05.2017.
 */
public class EntityDoesNotExistException extends Exception {
    public EntityDoesNotExistException(String message) {
        super(message);
    }

    public EntityDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
