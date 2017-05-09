package pb138.service.services;

import pb138.dal.entities.Item;

import java.util.List;

/**
 * Sends email alerts for all items below a threshold
 */
public interface EmailSender {
    /**
     * Sends email alerts for all items below a threshold
     * @param items items
     */
    void sendAlerts(List<Item> items);
}
