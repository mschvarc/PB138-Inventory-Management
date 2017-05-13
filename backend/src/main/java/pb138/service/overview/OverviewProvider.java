package pb138.service.overview;

import java.util.Date;
import java.util.List;
import pb138.service.exceptions.EntityDoesNotExistException;

public interface OverviewProvider {

    /**
     * Get daily count of sold items.
     *
     * @param ean - ean of item to find its sales
     * @param dayStart - date of the first day of the overview
     * @param numberOfDays - number of days of the overview
     * @return list of 'OverviewResultItem's with timespan one day
     * @throws EntityDoesNotExistException if item does not exists
     */
    List<OverviewResultItem> getDailySalesForItem(long ean, Date dayStart, int numberOfDays) throws EntityDoesNotExistException;

    /**
     * Get daily count of sold items from given category.
     *
     * @param categoryName - name of category to find its sales
     * @param dayStart - date of the first day of the overview
     * @param numberOfDays - number of days of the overview
     * @return list of 'OverviewResultCategory's with timespan one day
     * @throws EntityDoesNotExistException if category does not exists
     */
    List<OverviewResultCategory> getDailySalesForCategory(String categoryName, Date dayStart, int numberOfDays) throws EntityDoesNotExistException;

    /**
     * Get weekly count of sold items.
     *
     * @param ean - ean of item to find its sales
     * @param weekStart - date of some day from the first week of the overview
     * @param numberOfWeeks - number of weeks of the overview
     * @return list of 'OverviewResultItem's with timespan one week
     * @throws EntityDoesNotExistException if item does not exists
     */
    List<OverviewResultItem> getWeeklySalesForItem(long ean, Date weekStart, int numberOfWeeks) throws EntityDoesNotExistException;

    /**
     * Get weekly count of sold items from given category.
     *
     * @param categoryName - name of category to find its sales
     * @param weekStart - date of some day from the first week of the overview
     * @param numberOfWeeks - number of weeks of the overview
     * @return list of 'OverviewResultCategory's with timespan one week
     * @throws EntityDoesNotExistException if category does not exists
     */
    List<OverviewResultCategory> getWeeklySalesForCategory(String categoryName, Date weekStart, int numberOfWeeks) throws EntityDoesNotExistException;

    /**
     * Get monthly count of sold items.
     *
     * @param ean - ean of item to find its sales
     * @param monthStart - date of some day from the first month of the overview
     * @param numberOfMonths - number of months of the overview
     * @return list of 'OverviewResultItem's with timespan one month
     * @throws EntityDoesNotExistException if item does not exists
     */
    List<OverviewResultItem> getMonthlySalesForItem(long ean, Date monthStart, int numberOfMonths) throws EntityDoesNotExistException;

    /**
     * Get monthly count of sold items from given category.
     *
     * @param categoryName - name of category to find its sales
     * @param monthStart - date of some day from the first month of the overview
     * @param numberOfMonths - number of months of the overview
     * @return list of 'OverviewResultCategory's with timespan one month
     * @throws EntityDoesNotExistException if category does not exists
     */
    List<OverviewResultCategory> getMonthlySalesForCategory(String categoryName, Date monthStart, int numberOfMonths) throws EntityDoesNotExistException;

}
