package pb138.service.services;

import pb138.dal.entities.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements watchdog
 *
 */
public class ItemCountWatchdogServiceImpl implements ItemCountWatchdogService{
    private ItemService itemService;
    private EmailSender emailSender;

    /**
     * Constructor
     * @param itemService item service
     * @param emailSender email sender
     */
    public ItemCountWatchdogServiceImpl(ItemService itemService, EmailSender emailSender) {
        this.itemService = itemService;
        this.emailSender = emailSender;
    }

    @Override
    public void scanItems() {
        List<Item> items = itemService.getAllItems();
        List<Item> itemsBelowThreshold = new ArrayList<>();
        for (Item i : items) {
            Integer alertThreshold = i.getAlertThreshold();
            if (alertThreshold != null) {
                if (i.getCurrentCount() < alertThreshold) {
                    itemsBelowThreshold.add(i);
                }
            }
        }
        emailSender.sendAlerts(itemsBelowThreshold);

    }
}
