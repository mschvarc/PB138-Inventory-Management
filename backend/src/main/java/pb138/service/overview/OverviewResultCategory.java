package pb138.service.overview;

import com.migesok.jaxb.adapter.javatime.PeriodXmlAdapter;
import pb138.dal.entities.Category;
import pb138.service.dto.CategoryDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.Period;
import java.util.Date;

/**
 * Class representing overview of count of sold items from one category
 * in given timespan.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings({"unused"})
public class OverviewResultCategory implements Serializable {

    @XmlJavaTypeAdapter(PeriodXmlAdapter.class)
    private Period timespan;
    private Date startDate;
    private CategoryDto category;
    private int entityCount;

    /**
     * Constructor.
     */
    public OverviewResultCategory() {
        //Class pb138.service.overview.OverviewResult does not have a default constructor which JAXB requires.
    }

    /**
     * Constructor.
     *
     * @param timespan timespan of overview (expected a day, week or month)
     * @param startDate first day of overview
     * @param category items from this category are in the overview
     * @param entityCount - count of sold items
     */
    public OverviewResultCategory(Period timespan, Date startDate, CategoryDto category, int entityCount) {
        this.timespan = timespan;
        this.startDate = startDate;
        this.category = category;
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
     * Gets category
     *
     * @return value of category
     */
    public CategoryDto getCategory() {
        return category;
    }

    /**
     * Sets category
     *
     * @param category category
     */
    public void setCategory(CategoryDto category) {
        this.category = category;
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
