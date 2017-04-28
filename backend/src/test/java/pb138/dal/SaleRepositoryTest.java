package pb138.dal;

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
import pb138.service.filters.SaleFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/persistence-config.xml")
@Transactional
public class SaleRepositoryTest {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private SaleRepository repository;

    private Category category;
    private Item item;
    private Sale insertedSale;

    @Transactional
    @Before
    public void beforeTest() {
        category = new Category();
        category.setDescription("desc");
        category.setName("cat_name");
        manager.persist(category);

        item = new Item();
        item.setName("item");
        item.setDescription("desc");
        item.setCategory(category);
        item.setEan(5);
        item.setAlertThreshold(10);
        item.setUnit("pcs");
        manager.persist(item);

        insertedSale = new Sale();
        insertedSale.setQuantitySold(5);
        insertedSale.setDateSold(new Date());
        insertedSale.setItem(item);
        manager.persist(insertedSale);
    }


    @Test
    public void basicCreateRetrieveTest() {
        Sale sale = new Sale();
        sale.setQuantitySold(5);
        sale.setDateSold(new Date());
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
    public void complexFilteringTest() {
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
        assertThat(result.spliterator().getExactSizeIfKnown(), is(1L));
        assertThat(result, hasItem(sale));
    }


    @Test
    public void basicRetrieveTest() {
        Sale result = repository.getById(insertedSale.getId());
        assertThat(result, is(insertedSale));
    }


}
