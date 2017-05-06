package pb138.service.exceptions;

/**
 * Created by Jan on 06.05.2017.
 * This is thrown when we want to sale more that is currently in stock
 */
public class NotEnoughStoredException extends Exception{
    public NotEnoughStoredException(String message) {
        super(message);
    }

    public NotEnoughStoredException(String message, Throwable cause) {
        super(message, cause);
    }
}
