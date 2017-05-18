package pb138.service.overview;

import com.migesok.jaxb.adapter.javatime.PeriodXmlAdapter;
import pb138.dal.entities.Item;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.Period;
import java.util.Date;

/**
 * Class representing overview of count of sold items in given timespan.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings({"unused"})
public class OverviewResultItem implements Serializable {

    @XmlJavaTypeAdapter(PeriodXmlAdapter.class)
    private Period timespan;
    private Date startDate;
    private Item item;
    private int entityCount;

    /**
     * Constructor.
     */
    public OverviewResultItem() {
        //Class pb138.service.overview.OverviewResult does not have a default constructor which JAXB requires.
    }

    /**
     * Constructor.
     *
     * @param timespan timespan of overview (expected a day, week or month)
     * @param startDate first day of overview
     * @param item this items are in the overview
     * @param entityCount count of sold items
     */
    public OverviewResultItem(Period timespan, Date startDate, Item item, int entityCount) {
        this.timespan = timespan;
        this.startDate = startDate;
        this.item = item;
        this.entityCount = entityCount;
    }


    /**
     * Gets timespan
     *
     * @return value of timespan
     */
    public Period getTimespan() {
        return timespan;
    }

    /**
     * Sets timespan
     *
     * @param timespan timespan
     */
    public void setTimespan(Period timespan) {
        this.timespan = timespan;
    }

    /**
     * Gets startDate
     *
     * @return value of startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets startDate
     *
     * @param startDate startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets item
     *
     * @return value of item
     */
    public Item getItem() {
        return item;
    }

    /**
     * Sets item
     *
     * @param item item
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Gets entityCount
     *
     * @return value of entityCount
     */
    public int getEntityCount() {
        return entityCount;
    }

    /**
     * Sets entityCount
     *
     * @param entityCount entityCount
     */
    public void setEntityCount(int entityCount) {
        this.entityCount = entityCount;
    }
}
