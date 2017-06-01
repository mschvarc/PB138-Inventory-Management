package pb138.service.filters;

import pb138.dal.entities.Category;

/**
 * {@inheritDoc}
 */
public class ItemFilter implements EntityFilter {
    private Long id;
    private Category category;
    private String name;
    private Boolean fetchItemsBelowThreshold;
    private Long ean;

    /**
     * Items with count below specified threshold will be selected
     * @return items below threshold
     */
    public Boolean getFetchItemsBelowThreshold() {
        return fetchItemsBelowThreshold;
    }

    /**
     * Items with count below specified threshold will be selected
     *
     * @param fetchItemsBelowThreshold true to return below threshold only
     */
    public void setFetchItemsBelowThreshold(Boolean fetchItemsBelowThreshold) {
        this.fetchItemsBelowThreshold = fetchItemsBelowThreshold;
    }

    /**
     * Gets id
     *
     * @return value of id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id
     *
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
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
     * Gets ean
     *
     * @return value of ean
     */
    public Long getEan() {
        return ean;
    }

    /**
     * Sets ean
     *
     * @param ean ean
     */
    public void setEan(Long ean) {
        this.ean = ean;
    }
}
