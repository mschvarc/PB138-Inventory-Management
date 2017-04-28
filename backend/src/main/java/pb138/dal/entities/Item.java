package pb138.dal.entities;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Item {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @NotNull
    @Length(min = 1)
    private String name;

    @NotNull
    @Length(min = 1)
    private String description;

    @ManyToOne
    @NotNull
    private Category category;

    private Integer alertThreshold;

    @NotNull
    @Length(min = 1)
    private String unit;

    private int ean;

    @NotNull
    private int currentCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getAlertThreshold() {
        return alertThreshold;
    }

    public void setAlertThreshold(Integer alertThreshold) {
        this.alertThreshold = alertThreshold;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getEan() {
        return ean;
    }

    public void setEan(int ean) {
        this.ean = ean;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (getId() != item.getId()) return false;
        if (getEan() != item.getEan()) return false;
        if (getCurrentCount() != item.getCurrentCount()) return false;
        if (getName() != null ? !getName().equals(item.getName()) : item.getName() != null) return false;
        if (getDescription() != null ? !getDescription().equals(item.getDescription()) : item.getDescription() != null)
            return false;
        if (getCategory() != null ? !getCategory().equals(item.getCategory()) : item.getCategory() != null)
            return false;
        if (getAlertThreshold() != null ? !getAlertThreshold().equals(item.getAlertThreshold()) : item.getAlertThreshold() != null)
            return false;
        return getUnit() != null ? getUnit().equals(item.getUnit()) : item.getUnit() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getCategory() != null ? getCategory().hashCode() : 0);
        result = 31 * result + (getAlertThreshold() != null ? getAlertThreshold().hashCode() : 0);
        result = 31 * result + (getUnit() != null ? getUnit().hashCode() : 0);
        result = 31 * result + getEan();
        result = 31 * result + getCurrentCount();
        return result;
    }
}
