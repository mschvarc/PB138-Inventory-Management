package pb138.service.overview;

import java.time.Period;
import java.util.Calendar;
import java.util.List;
import javax.transaction.Transactional;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pb138.dal.entities.Item;
import pb138.dal.entities.Sale;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.facades.CategoryFacade;
import pb138.service.facades.CreateOrUpdate;
import pb138.service.facades.ItemFacade;
import pb138.service.facades.SaleFacade;
import pb138.utils.Pair;

/**
 * Tests for OverviewProviderImpl class.
 *
 * @author Marketa Elederova
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/persistence-config.xml")
@Transactional
public class OverviewProviderTest extends TestCase {

    @Autowired
    OverviewProvider overviewProvider;

    @Autowired
    private SaleFacade saleFacade;

    @Autowired
    private ItemFacade itemFacade;

    @Autowired
    private CategoryFacade categoryFacade;

    private Calendar calendar;

    @Before
    public void before() throws Exception {

        categoryFacade.createOrUpdateCategory("Electronics", "desc");
        categoryFacade.createOrUpdateCategory("Clothes", "desc");
        calendar = Calendar.getInstance();

        //123
        Pair<Item, CreateOrUpdate> i1 = itemFacade.createOrUpdateItem("PC", "desc", "Electronics", null, "pieces", 123);
        itemFacade.storeItemInDb(i1);
        itemFacade.updateItemFromWeb(123, 50, null, "pieces");

        calendar.set(2017, 1, 1);
        Sale s1i1 = saleFacade.addSale(123, calendar.getTime(), 2);
        saleFacade.storeSaleInDb(s1i1);

        calendar.set(2017, 1, 2, 0, 0, 0);
        Sale s2i1 = saleFacade.addSale(123, calendar.getTime(), 1);
        saleFacade.storeSaleInDb(s2i1);

        calendar.set(2017, 1, 7, 23, 59, 59);
        Sale s3i1 = saleFacade.addSale(123, calendar.getTime(), 3);
        saleFacade.storeSaleInDb(s3i1);

        calendar.set(2017, 1, 8);
        Sale s4i1 = saleFacade.addSale(123, calendar.getTime(), 1);
        saleFacade.storeSaleInDb(s4i1);

        calendar.set(2017, 2, 2);
        Sale s5i1 = saleFacade.addSale(123, calendar.getTime(), 1);
        saleFacade.storeSaleInDb(s5i1);

        calendar.set(2017, 0, 1);
        Sale s6i1 = saleFacade.addSale(123, calendar.getTime(), 2);
        saleFacade.storeSaleInDb(s6i1);

        //1234
        Pair<Item, CreateOrUpdate> i2 = itemFacade.createOrUpdateItem("TV", "desc", "Electronics", null, "pieces", 1234);
        itemFacade.storeItemInDb(i2);
        itemFacade.updateItemFromWeb(1234, 50, null, "pieces");

        calendar.set(2017, 1, 1);
        Sale s1i2 = saleFacade.addSale(1234, calendar.getTime(), 3);
        saleFacade.storeSaleInDb(s1i2);

        calendar.set(2017, 1, 5);
        Sale s2i2 = saleFacade.addSale(1234, calendar.getTime(), 1);
        saleFacade.storeSaleInDb(s2i2);
    }

    @Test
    public void getDailySalesForItem() throws EntityDoesNotExistException {

        calendar.set(2017, 1, 1, 13, 20);
        List<OverviewResultItem> results = overviewProvider.getDailySalesForItem(123, calendar.getTime(), 7);
        assertEquals(7, results.size());

        calendar.set(2017, 1, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        OverviewResultItem r1 = results.get(0);
        assertEquals(123, r1.getItem().getEan());
        assertEquals(calendar.getTime(), r1.getStartDate());
        assertEquals(Period.ofDays(1), r1.getTimespan());
        assertEquals(2, r1.getEntityCount());

        OverviewResultItem r2 = results.get(1);
        assertEquals(1, r2.getEntityCount());

        OverviewResultItem r3 = results.get(2);
        assertEquals(0, r3.getEntityCount());

        calendar.set(2017, 1, 7);
        OverviewResultItem r7 = results.get(6);
        assertEquals(calendar.getTime(), r7.getStartDate());
        assertEquals(Period.ofDays(1), r7.getTimespan());
        assertEquals(3, r7.getEntityCount());
    }

    @Test
    public void getDailySalesForCategory() throws EntityDoesNotExistException {

        calendar.set(2017, 1, 1, 13, 20);
        List<OverviewResultCategory> results = overviewProvider.getDailySalesForCategory("Electronics", calendar.getTime(), 7);
        assertEquals(7, results.size());

        calendar.set(2017, 1, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        OverviewResultCategory r1 = results.get(0);
        assertEquals("Electronics", r1.getCategory().getName());
        assertEquals(calendar.getTime(), r1.getStartDate());
        assertEquals(Period.ofDays(1), r1.getTimespan());
        assertEquals(5, r1.getEntityCount());

        OverviewResultCategory r7 = results.get(6);
        assertEquals(3, r7.getEntityCount());
    }

    @Test
    public void getWeeklySalesForItem() throws EntityDoesNotExistException {

        calendar.set(2017, 1, 1, 13, 20);
        List<OverviewResultItem> results = overviewProvider.getWeeklySalesForItem(123, calendar.getTime(), 5);
        assertEquals(5, results.size());

        calendar.set(2017, 0, 30, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        OverviewResultItem r1 = results.get(0);
        assertEquals(123, r1.getItem().getEan());
        assertEquals(calendar.getTime(), r1.getStartDate());
        assertEquals(Period.ofWeeks(1), r1.getTimespan());
        assertEquals(3, r1.getEntityCount());

        OverviewResultItem r2 = results.get(1);
        assertEquals(4, r2.getEntityCount());

        calendar.set(2017, 1, 27);
        OverviewResultItem r5 = results.get(4);
        assertEquals(calendar.getTime(), r5.getStartDate());
        assertEquals(1, r5.getEntityCount());
    }

    @Test
    public void getWeeklySalesForCategory() throws EntityDoesNotExistException {

        calendar.set(2017, 1, 5, 13, 20);
        List<OverviewResultCategory> results = overviewProvider.getWeeklySalesForCategory("Electronics", calendar.getTime(), 5);
        assertEquals(5, results.size());

        calendar.set(2017, 0, 30, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        OverviewResultCategory r1 = results.get(0);
        assertEquals("Electronics", r1.getCategory().getName());
        assertEquals(calendar.getTime(), r1.getStartDate());
        assertEquals(Period.ofWeeks(1), r1.getTimespan());
        assertEquals(7, r1.getEntityCount());

        OverviewResultCategory r4 = results.get(3);
        assertEquals(0, r4.getEntityCount());

        calendar.set(2017, 1, 27);
        OverviewResultCategory r5 = results.get(4);
        assertEquals(calendar.getTime(), r5.getStartDate());
        assertEquals(1, r5.getEntityCount());
    }

    @Test
    public void getMonthlySalesForItem() throws EntityDoesNotExistException {

        calendar.set(2017, 0, 31, 13, 20);
        List<OverviewResultItem> results = overviewProvider.getMonthlySalesForItem(123, calendar.getTime(), 3);
        assertEquals(3, results.size());

        calendar.set(2017, 0, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        OverviewResultItem r1 = results.get(0);
        assertEquals(123, r1.getItem().getEan());
        assertEquals(calendar.getTime(), r1.getStartDate());
        assertEquals(Period.ofMonths(1), r1.getTimespan());
        assertEquals(2, r1.getEntityCount());

        OverviewResultItem r2 = results.get(1);
        assertEquals(7, r2.getEntityCount());

        calendar.set(2017, 2, 1);
        OverviewResultItem r3 = results.get(2);
        assertEquals(calendar.getTime(), r3.getStartDate());
        assertEquals(1, r3.getEntityCount());
    }

    @Test
    public void getMonthlySalesForCategory() throws EntityDoesNotExistException {

        calendar.set(2017, 0, 31, 13, 20);
        List<OverviewResultCategory> results = overviewProvider.getMonthlySalesForCategory("Electronics", calendar.getTime(), 3);
        assertEquals(3, results.size());

        calendar.set(2017, 0, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        OverviewResultCategory r1 = results.get(0);
        assertEquals("Electronics", r1.getCategory().getName());
        assertEquals(calendar.getTime(), r1.getStartDate());
        assertEquals(Period.ofMonths(1), r1.getTimespan());
        assertEquals(2, r1.getEntityCount());

        OverviewResultCategory r2 = results.get(1);
        assertEquals(11, r2.getEntityCount());

        calendar.set(2017, 2, 1);
        OverviewResultCategory r3 = results.get(2);
        assertEquals(calendar.getTime(), r3.getStartDate());
        assertEquals(Period.ofMonths(1), r3.getTimespan());
        assertEquals(1, r3.getEntityCount());
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void getSalesForNonExistingItem() throws Exception {
        overviewProvider.getMonthlySalesForItem(456, calendar.getTime(), 3);
    }
}
