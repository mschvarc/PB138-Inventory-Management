package pb138.service.dto;


import java.io.Serializable;

/**
 * ItemDto
 */
public class ItemDto implements Serializable {
    private String name;
    private String description;
    private CategoryDto category;
    private Integer alertThreshold;
    private String unit;
    private int currentCount;
    private long ean; //unique business key

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
}
