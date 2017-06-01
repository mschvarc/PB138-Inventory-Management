package pb138.service.services;

/**
 * Class for watching whether current stock of items isn't below threshold
 */
public interface ItemCountWatchdogService {
    /**
     * Goes through all items and sends notification with those that are below threshold
     */
    void scanItems();
}
