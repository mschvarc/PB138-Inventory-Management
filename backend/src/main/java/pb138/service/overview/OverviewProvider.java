package pb138.service.overview;

import pb138.dal.entities.Category;
import pb138.dal.entities.Item;

import java.util.Date;
import java.util.List;

public interface OverviewProvider {

    //Marketa TODO: implement
    List<OverviewResult> getDailySalesForItem(Item item, Date dayStart, int numberOfDays);

    List<OverviewResult> getDailySalesForCategory(Category category, Date dayStart, int numberOfDays);

    List<OverviewResult> getWeeklySalesForItem(Item item, Date weekStart, int numberOfWeeks);

    List<OverviewResult> getWeeklySalesForCategory(Category category, Date weekStart, int numberOfWeeks);

    List<OverviewResult> getMonthlySalesForItem(Item item, Date monthStart, int numberOfMonths);

    List<OverviewResult> getMonthlySalesForCategory(Category category, Date monthStart, int numberOfMonths);

}
