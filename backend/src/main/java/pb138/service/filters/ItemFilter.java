package pb138.service.filters;

import pb138.dal.entities.Category;

public class ItemFilter {
    private Long id;
    private Category category;
    private String name;
    private Boolean fetchItemsBelowThreshold;

    /**
     * Items with count below specified threshold will be selected
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
