package pb138.service.filters;

import pb138.dal.entities.Category;
import pb138.dal.entities.Item;

import java.util.Date;

public class SaleFilter {
    private Long id;
    private Date dateSold;
    private Item item;
    private Category category;
    private Date dateSoldFrom;
    private Date dateSoldTo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateSold() {
        return dateSold;
    }

    public void setDateSold(Date dateSold) {
        this.dateSold = dateSold;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getDateSoldFrom() {
        return dateSoldFrom;
    }

    public void setDateSoldFrom(Date dateSoldFrom) {
        this.dateSoldFrom = dateSoldFrom;
    }

    public Date getDateSoldTo() {
        return dateSoldTo;
    }

    public void setDateSoldTo(Date dateSoldTo) {
        this.dateSoldTo = dateSoldTo;
    }
}
