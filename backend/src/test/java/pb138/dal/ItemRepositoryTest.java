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
import pb138.dal.repository.ItemRepository;
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.filters.ItemFilter;

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
public class ItemRepositoryTest {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private ItemRepository repository;

    private Category category;
    private Item item;
    private Sale sale;

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
        item.setCurrentCount(100);
        manager.persist(item);

        sale = new Sale();
        sale.setQuantitySold(5);
        sale.setDateSold(new Date());
        sale.setItem(item);
        manager.persist(sale);
    }


    @Test
    public void basicCreateRetrieveTest() throws Exception {
        Item item1 = new Item();
        item1.setCategory(category);
        item1.setEan(123);
        item1.setUnit("pcs");
        item1.setCurrentCount(10);
        item1.setName("item");
        item1.setAlertThreshold(30);
        item1.setDescription("desc");
        repository.create(item1);

        Item result = repository.getById(item1.getId());
        assertNotNull(result);
        assertEquals(result.getId(), item1.getId());
    }

    @Test
    public void basicFilteringTest() {
        ItemFilter filter = new ItemFilter();
        filter.setId(item.getId());
        Iterable<Item> result = repository.find(filter);
        assertThat(result, hasItem(item));
    }

    @Test
    public void basicFilteringEntityTest() {
        ItemFilter filter = new ItemFilter();
        filter.setEan(item.getEan());
        filter.setId(item.getId());
        filter.setCategory(category);
        Iterable<Item> result = repository.find(filter);
        assertThat(result, hasItem(item));
        boolean found = false;
        for (Item i : result) {
            if (i.getCategory().equals(category)) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void complexFilteringTest() throws Exception {
        Item item1 = new Item();
        item1.setCategory(category);
        item1.setEan(123);
        item1.setUnit("pcs");
        item1.setCurrentCount(10);
        item1.setName("item");
        item1.setAlertThreshold(30);
        item1.setDescription("desc");
        repository.create(item1);

        ItemFilter filter = new ItemFilter();
        filter.setEan(item1.getEan());
        filter.setId(item1.getId());
        filter.setCategory(category);
        filter.setName("item");
        filter.setFetchItemsBelowThreshold(true);

        Iterable<Item> result = repository.find(filter);
        assertThat(result.spliterator().getExactSizeIfKnown(), is(1L));
        assertThat(result, hasItem(item1));
    }


    @Test
    public void basicRetrieveTest() {
        Item result = repository.getById(item.getId());
        assertThat(result, is(item));
    }

    @Test(expected = EntityValidationException.class)
    public void testValidation() throws Exception {
        Item item1 = new Item();
        item1.setCategory(null);
        item1.setEan(123);
        item1.setUnit(null);
        item1.setCurrentCount(10);
        item1.setName(null);
        item1.setAlertThreshold(30);
        item1.setDescription(null);
        repository.create(item1);
    }
}
