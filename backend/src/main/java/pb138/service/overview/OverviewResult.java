package pb138.service.overview;

import com.migesok.jaxb.adapter.javatime.PeriodXmlAdapter;
import pb138.dal.entities.Category;
import pb138.dal.entities.Item;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.Period;
import java.util.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings({"unused"})
public class OverviewResult {

    @XmlJavaTypeAdapter(PeriodXmlAdapter.class)
    private Period timespan;

    private Date startDate;
    private Category category;
    private Item item;
    private int entityCount;

    public OverviewResult() {
        //Class pb138.service.overview.OverviewResult does not have a default constructor which JAXB requires.
    }

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

    public void setTimespan(Period timespan) {
        this.timespan = timespan;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getEntityCount() {
        return entityCount;
    }

    public void setEntityCount(int entityCount) {
        this.entityCount = entityCount;
    }
}
