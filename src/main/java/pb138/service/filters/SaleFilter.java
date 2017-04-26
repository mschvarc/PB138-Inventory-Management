package pb138.service.filters;

import pb138.dal.entities.Category;
import pb138.dal.entities.Item;

import java.util.Date;

public class SaleFilter {
    private Date startDate;
    private Integer count;
    private Item item;
    private Category category;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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
}
