package pb138.service.overview;

import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import pb138.dal.entities.Category;
import pb138.dal.entities.Item;
import pb138.dal.entities.Sale;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.facades.SaleFacade;
import pb138.service.services.CategoryService;
import pb138.service.services.ItemService;

/**
 *
 * @author Marketa Elederova
 */
public class OverviewProviderImpl implements OverviewProvider {

    private SaleFacade saleFacade;
    private ItemService itemService;
    private CategoryService categoryService;

    public OverviewProviderImpl(SaleFacade saleFacade, ItemService itemService, CategoryService categoryService) {
        this.saleFacade = saleFacade;
        this.itemService = itemService;
        this.categoryService = categoryService;
    }

    @Override
    public List<OverviewResultItem> getDailySalesForItem(long ean, Date dayStart, int numberOfDays)
            throws EntityDoesNotExistException {

        return getSalesForItem(ean, dayStart, numberOfDays, Calendar.DAY_OF_MONTH);
    }

    @Override
    public List<OverviewResultCategory> getDailySalesForCategory(String categoryName, Date dayStart, int numberOfDays)
            throws EntityDoesNotExistException {

        return getSalesForCategory(categoryName, dayStart, numberOfDays, Calendar.DAY_OF_MONTH);
    }

    @Override
    public List<OverviewResultItem> getWeeklySalesForItem(long ean, Date weekStart, int numberOfWeeks)
            throws EntityDoesNotExistException {

        return getSalesForItem(ean, weekStart, numberOfWeeks, Calendar.WEEK_OF_YEAR);
    }

    @Override
    public List<OverviewResultCategory> getWeeklySalesForCategory(String categoryName, Date weekStart, int numberOfWeeks)
            throws EntityDoesNotExistException {

        return getSalesForCategory(categoryName, weekStart, numberOfWeeks, Calendar.WEEK_OF_YEAR);
    }

    @Override
    public List<OverviewResultItem> getMonthlySalesForItem(long ean, Date monthStart, int numberOfMonths)
            throws EntityDoesNotExistException {

        return getSalesForItem(ean, monthStart, numberOfMonths, Calendar.MONTH);
    }

    @Override
    public List<OverviewResultCategory> getMonthlySalesForCategory(String categoryName, Date monthStart, int numberOfMonths)
            throws EntityDoesNotExistException {

        return getSalesForCategory(categoryName, monthStart, numberOfMonths, Calendar.MONTH);
    }

    private List<OverviewResultItem> getSalesForItem(long ean, Date start, int numberOfPeriods, int calFieldPeriod)
            throws EntityDoesNotExistException {

        Item item = itemService.getByEan(ean);
        if (item == null) {
            throw new EntityDoesNotExistException("Item with ean " + ean + " does not exist");
        }

        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        to.setTime(start);
        roud(to, calFieldPeriod);
        List<OverviewResultItem> results = new ArrayList<>();
        List<Sale> sales;
        Period period;
        switch(calFieldPeriod) {
            case Calendar.DAY_OF_MONTH:
                period = Period.ofDays(1);
                break;
            case Calendar.WEEK_OF_YEAR:
                period = Period.ofWeeks(1);
                break;
            case Calendar.MONTH:
                period = Period.ofMonths(1);
                break;
            default:
                period = null;
        }

        for (int i = 0; i < numberOfPeriods; ++i) {
            from.setTime(to.getTime());
            to.add(calFieldPeriod, 1);

            sales = saleFacade.getSalesForProduct(item, from.getTime(), to.getTime());
            int itemCount = 0;
            for (Sale sale : sales) {
                itemCount += sale.getQuantitySold();
            }
            results.add(new OverviewResultItem(period, from.getTime(), item, itemCount));
        }

        return results;
    }

    private List<OverviewResultCategory> getSalesForCategory(String categoryName, Date start, int numberOfPeriods, int calFieldPeriod)
            throws EntityDoesNotExistException {

        Category category = categoryService.getByName(categoryName);
        if (category == null) {
            throw new EntityDoesNotExistException("Category " + categoryName + " does not exist");
        }

        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        to.setTime(start);
        roud(to, calFieldPeriod);
        List<OverviewResultCategory> results = new ArrayList<>();
        List<Sale> sales;
        Period period;
        switch(calFieldPeriod) {
            case Calendar.DAY_OF_MONTH:
                period = Period.ofDays(1);
                break;
            case Calendar.WEEK_OF_YEAR:
                period = Period.ofWeeks(1);
                break;
            case Calendar.MONTH:
                period = Period.ofMonths(1);
                break;
            default:
                period = null;
        }

        for (int i = 0; i < numberOfPeriods; ++i) {
            from.setTime(to.getTime());
            to.add(calFieldPeriod, 1);

            sales = saleFacade.getSalesForCategory(category, from.getTime(), to.getTime());
            int itemCount = 0;
            for (Sale sale : sales) {
                itemCount += sale.getQuantitySold();
            }
            results.add(new OverviewResultCategory(period, from.getTime(), category, itemCount));
        }

        return results;
    }

    //works only for calFieldAccuracy values: Calendar.DAY_OF_MONTH, Calendar.WEEK_OF_YEAR and Calendar.MONTH
    private Calendar roud(Calendar date, int calFieldAccuracy) {

        date.set(Calendar.MILLISECOND, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.HOUR_OF_DAY, 0);

        if (calFieldAccuracy == Calendar.WEEK_OF_YEAR) {
            date.setFirstDayOfWeek(Calendar.MONDAY);
            date.set(Calendar.DAY_OF_WEEK, 2);
            return date;
        }

        if (calFieldAccuracy == Calendar.MONTH) {
            date.set(Calendar.DAY_OF_MONTH, 1);
            return date;
        }

        return date;
    }
}
