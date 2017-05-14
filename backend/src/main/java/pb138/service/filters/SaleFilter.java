package pb138.service.filters;

import pb138.dal.entities.Category;
import pb138.dal.entities.Item;

import java.util.Date;

/**
 * {@inheritDoc}
 */
public class SaleFilter implements EntityFilter {
    private Long id;
    private Date dateSold;
    private Item item;
    private Category category;
    private Date dateSoldFrom;
    private Date dateSoldTo;


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
     * Gets dateSold
     *
     * @return value of dateSold
     */
    public Date getDateSold() {
        return dateSold;
    }

    /**
     * Sets dateSold
     *
     * @param dateSold dateSold
     */
    public void setDateSold(Date dateSold) {
        this.dateSold = dateSold;
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
     * Gets dateSoldFrom INCLUSIVE
     *
     * @return value of dateSoldFrom
     */
    public Date getDateSoldFrom() {
        return dateSoldFrom;
    }

    /**
     * Sets dateSoldFrom INCLUSIVE
     *
     * @param dateSoldFrom dateSoldFrom
     */
    public void setDateSoldFrom(Date dateSoldFrom) {
        this.dateSoldFrom = dateSoldFrom;
    }

    /**
     * Gets dateSoldTo EXCLUSIVE
     *
     * @return value of dateSoldTo
     */
    public Date getDateSoldTo() {
        return dateSoldTo;
    }

    /**
     * Sets dateSoldTo EXCLUSIVE
     *
     * @param dateSoldTo dateSoldTo
     */
    public void setDateSoldTo(Date dateSoldTo) {
        this.dateSoldTo = dateSoldTo;
    }
}
