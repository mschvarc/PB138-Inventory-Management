package pb138.service.exceptions;

/**
 * Created by Jan on 06.05.2017.
 * This is thrown when we want to sale more that is currently in stock
 */
public class NotEnughStoredException  extends Exception{
    public NotEnughStoredException(String message) {
        super(message);
    }

    public NotEnughStoredException(String message, Throwable cause) {
        super(message, cause);
    }
}
