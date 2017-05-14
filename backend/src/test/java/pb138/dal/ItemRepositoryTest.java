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
import pb138.dal.repository.ItemRepository;
import pb138.dal.repository.validation.ConstraintValidator;
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.filters.ItemFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Tests ItemRepositoryImpl
 * @author Martin Schvarcbacher
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/persistence-config.xml")
@Transactional
public class ItemRepositoryTest extends TestCase {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private ItemRepository repository;

    @Autowired
    private ConstraintValidator validator;

    private Category category;
    private Item item;
    private Sale sale;

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
        item.setCurrentCount(100);
        validator.validate(item);
        manager.persist(item);

        sale = new Sale();
        sale.setQuantitySold(5);
        sale.setDateSold(new Date());
        sale.setItem(item);
        validator.validate(sale);
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
    public void updatetest() throws Exception {
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
        result.setDescription("updated desc");
        result.setName("updated name");
        result.setUnit("kg");
        result.setCurrentCount(20);
        repository.update(result);
        result = repository.getById(item1.getId());
        assertThat(result.getDescription(), is(equalTo("updated desc")));
        assertThat(result.getCurrentCount(), is(equalTo(20)));
        assertThat(result.getName(), is(equalTo("updated name")));
        assertThat(result.getUnit(), is(equalTo("kg")));
    }

    @Test
    public void deleteTest() throws Exception {
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

        repository.delete(result);
        result = repository.getById(item1.getId());
        assertNull(result);
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

    @Test(expected = EntityValidationException.class)
    public void duplicateEanTest() throws Exception {
        Item i1 = new Item();
        i1.setCategory(category);
        i1.setDescription("desc1");
        i1.setName("name1");
        i1.setCurrentCount(5);
        i1.setEan(123);
        i1.setUnit("kg");
        repository.create(i1);
        Item r1 = repository.getById(i1.getId());
        assertEquals(r1, i1);

        Item i2 = new Item();
        i2.setCategory(category);
        i2.setDescription("desc2");
        i2.setName("name2");
        i2.setCurrentCount(10);
        i2.setEan(123); //<<duplicate ean, ean is business key
        i2.setUnit("pcs");
        assertThat(i2, is(i1));
        repository.create(i2);
    }


    @Test
    public void nullThresholdTest() throws Exception {
        Item nullThresholdItem = new Item();
        nullThresholdItem.setCategory(category);
        nullThresholdItem.setDescription("desc1");
        nullThresholdItem.setName("name1");
        nullThresholdItem.setCurrentCount(10);
        nullThresholdItem.setEan(123);
        nullThresholdItem.setUnit("kg");
        nullThresholdItem.setAlertThreshold(null);
        repository.create(nullThresholdItem);
        Item r1 = repository.getById(nullThresholdItem.getId());
        assertEquals(r1, nullThresholdItem);

        Item setThresholdItem = new Item();
        setThresholdItem.setCategory(category);
        setThresholdItem.setDescription("desc2");
        setThresholdItem.setName("name2");
        setThresholdItem.setCurrentCount(1);
        setThresholdItem.setEan(444);
        setThresholdItem.setUnit("pcs");
        setThresholdItem.setAlertThreshold(10);
        repository.create(setThresholdItem);

        ItemFilter filter = new ItemFilter();
        filter.setFetchItemsBelowThreshold(true);
        filter.setCategory(category);
        Iterable<Item> items = repository.find(filter);
        List<Item> list = new ArrayList<Item>();
        items.forEach(list::add);

        assertThat(list.size(), is(1));
        assertThat(items, not(hasItem(nullThresholdItem)));
        assertThat(items, hasItem(setThresholdItem));
    }


    @Test
    public void basicRetrieveTest() {
        Item result = repository.getById(item.getId());
        assertThat(result, is(item));
    }

    @Test(expected = EntityValidationException.class)
    public void testValidationNested() throws Exception {
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

    @Test(expected = EntityValidationException.class)
    public void testValidation() throws Exception {
        Item item1 = new Item();
        item1.setCategory(category);
        item1.setEan(123);
        item1.setUnit(null);
        item1.setCurrentCount(10);
        item1.setName(null);
        item1.setAlertThreshold(30);
        item1.setDescription(null);
        repository.create(item1);
    }

}
