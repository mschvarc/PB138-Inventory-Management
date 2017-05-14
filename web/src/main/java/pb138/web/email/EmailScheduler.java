package pb138.web.email;

import org.springframework.scheduling.annotation.Scheduled;
import pb138.service.services.ItemCountWatchdogService;

/**
 * Spring Scheduled class for sending emails every 24 hours
 * @author Martin Schvarcbacher
 */
public class EmailScheduler {

    private final ItemCountWatchdogService itemCountWatchdogService;
    private final boolean enabled;

    private final EmailConfigLoader emailConfigLoader;

    public EmailScheduler(ItemCountWatchdogService itemCountWatchdogService, EmailConfigLoader emailConfigLoader) {
        this.itemCountWatchdogService = itemCountWatchdogService;
        this.emailConfigLoader = emailConfigLoader;
        enabled = emailConfigLoader.extractEmailConfig().get("enabled").equalsIgnoreCase("true");
    }

    @Scheduled(initialDelay = 0, fixedDelay = 2 * 60 * 1000L)
    public void executeScan() {
        if (!enabled) {
            System.err.println(">>>>EMAIL SENDING DISABLED<<<<");
            return;
        }
        System.err.println(">>>>EMAIL TASK SCHEDULER<<<<");
        itemCountWatchdogService.scanItems();
    }
}
