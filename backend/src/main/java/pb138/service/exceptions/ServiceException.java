package pb138.service.exceptions;

/**
 * Created by Honza on 30.04.2017.
 */
public class ServiceException extends Exception {
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message) {
        super(message);
    }
}
