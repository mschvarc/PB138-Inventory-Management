package pb138.service.overview;

import pb138.dal.entities.Category;
import pb138.dal.entities.Item;

import java.util.Date;
import java.util.List;

public interface OverviewProvider {

    //Marketa TODO: implement
    List<OverviewResultItem> getDailySalesForItem(Item item, Date dayStart, int numberOfDays);

    List<OverviewResultCategory> getDailySalesForCategory(Category category, Date dayStart, int numberOfDays);

    List<OverviewResultItem> getWeeklySalesForItem(Item item, Date weekStart, int numberOfWeeks);

    List<OverviewResultCategory> getWeeklySalesForCategory(Category category, Date weekStart, int numberOfWeeks);

    List<OverviewResultItem> getMonthlySalesForItem(Item item, Date monthStart, int numberOfMonths);

    List<OverviewResultCategory> getMonthlySalesForCategory(Category category, Date monthStart, int numberOfMonths);

}
