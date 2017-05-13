package pb138.service.overview;

import java.util.Date;
import java.util.List;
import pb138.service.exceptions.EntityDoesNotExistException;

public interface OverviewProvider {

    List<OverviewResultItem> getDailySalesForItem(long ean, Date dayStart, int numberOfDays) throws EntityDoesNotExistException;

    List<OverviewResultCategory> getDailySalesForCategory(String categoryName, Date dayStart, int numberOfDays) throws EntityDoesNotExistException;

    List<OverviewResultItem> getWeeklySalesForItem(long ean, Date weekStart, int numberOfWeeks) throws EntityDoesNotExistException;

    List<OverviewResultCategory> getWeeklySalesForCategory(String categoryName, Date weekStart, int numberOfWeeks) throws EntityDoesNotExistException;

    List<OverviewResultItem> getMonthlySalesForItem(long ean, Date monthStart, int numberOfMonths) throws EntityDoesNotExistException;

    List<OverviewResultCategory> getMonthlySalesForCategory(String categoryName, Date monthStart, int numberOfMonths) throws EntityDoesNotExistException;

}
