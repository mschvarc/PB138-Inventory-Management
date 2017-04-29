package pb138.service.filters;

import pb138.dal.entities.Category;
import pb138.dal.entities.Item;

import java.util.Date;

public class ShipmentFilter {
    private Long id;
    private Date dateImported;
    private Item item;
    private Category category;
    private Date dateImportedFrom;
    private Date dateImportedTo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateImported() {
        return dateImported;
    }

    public void setDateImported(Date dateImported) {
        this.dateImported = dateImported;
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

    public Date getDateImportedFrom() {
        return dateImportedFrom;
    }

    public void setDateImportedFrom(Date dateImportedFrom) {
        this.dateImportedFrom = dateImportedFrom;
    }

    public Date getDateImportedTo() {
        return dateImportedTo;
    }

    public void setDateImportedTo(Date dateImportedTo) {
        this.dateImportedTo = dateImportedTo;
    }
}
