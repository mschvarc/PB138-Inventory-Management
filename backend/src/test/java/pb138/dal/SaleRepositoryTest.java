package pb138.dal;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pb138.dal.entities.Category;
import pb138.dal.entities.Item;
import pb138.dal.entities.Sale;
import pb138.dal.repository.SaleRepository;
import pb138.dal.repository.validation.ConstraintValidator;
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.filters.SaleFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/persistence-config.xml")
@Transactional
public class SaleRepositoryTest extends TestCase {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private SaleRepository repository;

    @Autowired
    private ConstraintValidator validator;

    private Category category;
    private Item item;
    private Sale insertedSale;

    private static Date date(long stamp) {
        Date date = new Date();
        date.setTime(stamp);
        return date;
    }

    @Transactional
    @Before
    public void beforeTest() throws Exception {
        category = new Category();
        category.setDescription("desc");
        category.setName("cat_name");
        validator.validate(category);
        manager.persist(category);

        item = new Item();
        item.setName("item");
        item.setDescription("desc");
        item.setCategory(category);
        item.setEan(5);
        item.setAlertThreshold(10);
        item.setUnit("pcs");
        validator.validate(item);
        manager.persist(item);

        insertedSale = new Sale();
        insertedSale.setQuantitySold(5);
        Date date = new Date();
        date.setTime(600L);
        insertedSale.setDateSold(date);
        insertedSale.setItem(item);
        validator.validate(item);
        manager.persist(insertedSale);
    }

    @Test
    public void basicCreateRetrieveTest() throws Exception {
        Sale sale = new Sale();
        sale.setQuantitySold(5);
        sale.setDateSold(new Date());
        sale.setItem(item);
        repository.create(sale);
        Sale result = repository.getById(sale.getId());
        assertNotNull(result);
        assertEquals(result.getId(), sale.getId());
    }

    @Test
    public void basicFilteringTest() {
        SaleFilter filter = new SaleFilter();
        filter.setId(insertedSale.getId());
        Iterable<Sale> result = repository.find(filter);
        assertThat(result, hasItem(insertedSale));
    }

    @Test
    public void basicFilteringEntityTest() {
        SaleFilter filter = new SaleFilter();
        filter.setItem(item);
        Iterable<Sale> result = repository.find(filter);
        assertThat(result, hasItem(insertedSale));
        boolean found = false;
        for (Sale sale : result) {
            if (sale.getItem().equals(item)) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void complexFilteringTest() throws Exception {
        Sale sale = new Sale();
        sale.setQuantitySold(25);
        Date date = new Date();
        date.setTime(500L);
        sale.setDateSold(date);
        sale.setItem(item);
        sale.getItem().setCategory(category);
        repository.create(sale);

        SaleFilter filter = new SaleFilter();
        filter.setDateSold(date);
        filter.setCategory(category);
        filter.setItem(item);
        filter.setId(sale.getId());
        Iterable<Sale> result = repository.find(filter);
        assertThat(result.spliterator().getExactSizeIfKnown(), is(equalTo(1L)));
        assertThat(result, hasItem(sale));
    }

    @Test
    public void basicRetrieveTest() {
        Sale result = repository.getById(insertedSale.getId());
        assertThat(result, is(insertedSale));
    }

    @Test(expected = EntityValidationException.class)
    public void testValidation() throws Exception {
        Sale sale = new Sale();
        sale.setQuantitySold(-5);
        sale.setDateSold(null);
        sale.setItem(null);
        repository.create(sale);
    }

    @Test
    public void dateFilteringTestFrom() throws Exception {
        Sale sale = new Sale();
        sale.setQuantitySold(25);
        Date dateSold = new Date();
        sale.setDateSold(date(500L));
        sale.setItem(item);
        sale.getItem().setCategory(category);
        repository.create(sale);

        SaleFilter filter = new SaleFilter();
        filter.setCategory(category);

        filter.setDateSoldFrom(date(400L));
        Iterable<Sale> result = repository.find(filter);
        assertThat(result.spliterator().getExactSizeIfKnown(), is(equalTo(2L)));
        assertThat(result, hasItems(sale, insertedSale));
    }

    @Test
    public void dateFilteringTestFromTo() throws Exception {
        Sale sale = new Sale();
        sale.setQuantitySold(25);
        Date dateSold = new Date();
        sale.setDateSold(date(500L));
        sale.setItem(item);
        sale.getItem().setCategory(category);
        repository.create(sale);

        SaleFilter filter = new SaleFilter();
        filter.setCategory(category);

        filter.setDateSoldFrom(date(400L));
        filter.setDateSoldTo(date(550L));
        Iterable<Sale> result = repository.find(filter);
        assertThat(result.spliterator().getExactSizeIfKnown(), is(1L));
        assertThat(result, hasItem(sale));
    }

    @Test
    public void equalityTest() throws Exception {
        //https://vladmihalcea.com/2013/10/23/hibernate-facts-equals-and-hashcode/
        Sale sale = new Sale();
        sale.setDateSold(insertedSale.getDateSold());
        sale.setQuantitySold(insertedSale.getQuantitySold());
        sale.setItem(insertedSale.getItem());
        assertEquals(sale, insertedSale); //using business key, objects must equal before persistence
        repository.create(sale);
        assertEquals(sale, insertedSale);
    }


    @Test
    public void updateTest() throws Exception {
        Sale sale = new Sale();
        sale.setQuantitySold(25);
        Date dateSold = new Date();
        sale.setDateSold(date(500L));
        sale.setItem(item);
        sale.getItem().setCategory(category);
        repository.create(sale);

        Sale saleRetrieved = repository.getById(sale.getId());
        assertNotNull(sale);
        saleRetrieved.setQuantitySold(100);
        saleRetrieved.setDateSold(date(900));
        repository.update(saleRetrieved);

        Sale saleUpdatedRetrieved = repository.getById(sale.getId());
        assertThat(saleUpdatedRetrieved.getQuantitySold(), is(100));
        assertThat(saleUpdatedRetrieved.getDateSold(), is(date(900)));
        assertThat(saleRetrieved, is(saleUpdatedRetrieved));
    }

    @Test
    public void deleteTest() throws Exception {
        Sale sale = new Sale();
        sale.setQuantitySold(5);
        sale.setDateSold(new Date());
        sale.setItem(item);
        repository.create(sale);
        Sale result = repository.getById(sale.getId());
        assertNotNull(result);
        repository.delete(result);
        result = repository.getById(sale.getId());
        assertNull(result);
    }

}
