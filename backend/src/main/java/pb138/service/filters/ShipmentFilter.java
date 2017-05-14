package pb138.service.filters;

import pb138.dal.entities.Category;
import pb138.dal.entities.Item;

import java.util.Date;

/**
 * {@inheritDoc}
 */
public class ShipmentFilter implements EntityFilter {
    private Long id;
    private Date dateImported;
    private Item item;
    private Category category;
    private Date dateImportedFrom;
    private Date dateImportedTo;

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
     * Gets dateImported
     *
     * @return value of dateImported
     */
    public Date getDateImported() {
        return dateImported;
    }

    /**
     * Sets dateImported
     *
     * @param dateImported dateImported
     */
    public void setDateImported(Date dateImported) {
        this.dateImported = dateImported;
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
     * Gets dateImportedFrom INCLUSIVE
     *
     * @return value of dateImportedFrom
     */
    public Date getDateImportedFrom() {
        return dateImportedFrom;
    }

    /**
     * Sets dateImportedFrom INCLUSIVE
     *
     * @param dateImportedFrom dateImportedFrom
     */
    public void setDateImportedFrom(Date dateImportedFrom) {
        this.dateImportedFrom = dateImportedFrom;
    }

    /**
     * Gets dateImportedTo EXCLUSIVE
     *
     * @return value of dateImportedTo
     */
    public Date getDateImportedTo() {
        return dateImportedTo;
    }

    /**
     * Sets dateImportedTo EXCLUSIVE
     *
     * @param dateImportedTo dateImportedTo
     */
    public void setDateImportedTo(Date dateImportedTo) {
        this.dateImportedTo = dateImportedTo;
    }
}
