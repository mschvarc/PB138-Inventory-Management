package pb138.service.overview;

import pb138.dal.entities.Category;
import pb138.dal.entities.Item;

import java.time.Period;
import java.util.Date;

public class OverviewResult {
    private Period timespan;
    private Date startDate;
    private Category category;
    private Item item;
    private int entityCount;

    public OverviewResult(Period timespan, Date startDate, Category category, int entityCount) {
        this.timespan = timespan;
        this.startDate = startDate;
        this.category = category;
        this.entityCount = entityCount;
    }

    public OverviewResult(Period timespan, Date startDate, Item item, int entityCount) {
        this.timespan = timespan;
        this.startDate = startDate;
        this.item = item;
        this.entityCount = entityCount;
    }

    public Period getTimespan() {
        return timespan;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Category getCategory() {
        return category;
    }

    public Item getItem() {
        return item;
    }

    /**
     * @return Number of items / items within a category sold
     */
    public int getEntityCount() {
        return entityCount;
    }
}
