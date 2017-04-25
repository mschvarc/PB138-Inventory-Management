package service.filters;

import service.dto.CategoryDto;
import service.dto.ItemDto;

import java.util.Date;

public class SaleFilter {
    private Date startDate;
    private Integer count;
    private ItemDto item;
    private CategoryDto category;

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

    public ItemDto getItem() {
        return item;
    }

    public void setItem(ItemDto item) {
        this.item = item;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }
}
