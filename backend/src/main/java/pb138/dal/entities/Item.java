package pb138.dal.entities;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * ORM Item
 * PK: id
 * BK: ean
 */
@Entity
public class Item {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @NotNull
    @Length(min = 1)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Length(min = 1)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Category category;

    private Integer alertThreshold;

    @NotNull
    @Length(min = 1)
    private String unit;

    //business key
    @Min(0)
    @NotNull
    @Column(nullable = false, unique = true)
    private long ean;

    @NotNull
    private int currentCount;

    /**
     * Gets id
     *
     * @return value of id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id
     *
     * @param id id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets name
     *
     * @return value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets description
     *
     * @return value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description
     *
     * @param description description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets category
     *
     * @return value of category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets category
     *
     * @param category category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Gets alertThreshold
     *
     * @return value of alertThreshold
     */
    public Integer getAlertThreshold() {
        return alertThreshold;
    }

    /**
     * Sets alertThreshold
     *
     * @param alertThreshold alertThreshold
     */
    public void setAlertThreshold(Integer alertThreshold) {
        this.alertThreshold = alertThreshold;
    }

    /**
     * Gets unit
     *
     * @return value of unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets unit
     *
     * @param unit unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Gets ean
     *
     * @return value of ean
     */
    public long getEan() {
        return ean;
    }

    /**
     * Sets ean
     *
     * @param ean ean
     */
    public void setEan(long ean) {
        this.ean = ean;
    }

    /**
     * Gets currentCount
     *
     * @return value of currentCount
     */
    public int getCurrentCount() {
        return currentCount;
    }

    /**
     * Sets currentCount
     *
     * @param currentCount currentCount
     */
    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }

        Item item = (Item) o;

        return getEan() == item.getEan();
    }

    @Override
    public int hashCode() {
        return (int) (getEan() ^ (getEan() >>> 32));
    }
}
