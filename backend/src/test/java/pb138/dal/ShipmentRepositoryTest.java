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
import pb138.dal.entities.Shipment;
import pb138.dal.repository.ShipmentRepository;
import pb138.dal.repository.validation.ConstraintValidator;
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.filters.ShipmentFilter;

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
public class ShipmentRepositoryTest extends TestCase {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private ShipmentRepository repository;

    @Autowired
    private ConstraintValidator validator;

    private Category category;
    private Item item;
    private Shipment insertedShipment;

    private static Date date(long stamp) {
        Date date = new Date();
        date.setTime(stamp);
        return date;
    }

    @Transactional
    @Before
    public void beforeTest() throws EntityValidationException {
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

        insertedShipment = new Shipment();
        insertedShipment.setQuantityImported(5);
        insertedShipment.setDateImported(date(600*1000L));
        insertedShipment.setItem(item);
        validator.validate(insertedShipment);
        manager.persist(insertedShipment);
    }

    @Test
    public void basicCreateRetrieveTest() throws Exception {
        Shipment shipment = new Shipment();
        shipment.setQuantityImported(5);
        shipment.setDateImported(date(500L));
        shipment.setItem(item);
        repository.create(shipment);
        Shipment result = repository.getById(shipment.getId());
        assertNotNull(result);
        assertEquals(result.getId(), shipment.getId());
    }

    @Test
    public void updateTest() throws Exception {
        Shipment shipment = new Shipment();
        shipment.setQuantityImported(5);
        shipment.setDateImported(date(500L));
        shipment.setItem(item);
        repository.create(shipment);
        Shipment result = repository.getById(shipment.getId());
        result.setQuantityImported(10);
        result.setDateImported(date(999));
        repository.update(result);
        result = repository.getById(result.getId());
        assertThat(result.getDateImported(), is(equalTo(date(999))));
        assertThat(result.getQuantityImported(), is(equalTo(10)));
        assertEquals(result, shipment);
    }

    @Test
    public void deleteTest() throws Exception {
        Shipment shipment = new Shipment();
        shipment.setQuantityImported(5);
        shipment.setDateImported(date(500L));
        shipment.setItem(item);
        repository.create(shipment);
        Shipment result = repository.getById(shipment.getId());
        assertNotNull(result);
        assertEquals(result.getId(), shipment.getId());
        repository.delete(result);
        result = repository.getById(shipment.getId());
        assertNull(result);
    }

    @Test
    public void complexFilteringTest() throws Exception {
        Shipment shipment = new Shipment();
        shipment.setQuantityImported(5);
        shipment.setDateImported(date(500L));
        shipment.setItem(item);
        repository.create(shipment);

        ShipmentFilter filter = new ShipmentFilter();
        filter.setDateImported(date(500L));
        filter.setCategory(category);
        filter.setItem(item);
        filter.setId(shipment.getId());
        Iterable<Shipment> result = repository.find(filter);
        assertThat(result.spliterator().getExactSizeIfKnown(), is(1L));
        assertThat(result, hasItem(shipment));
    }

    @Test
    public void basicRetrieveTest() {
        Shipment result = repository.getById(insertedShipment.getId());
        assertThat(result, is(insertedShipment));
    }

    @Test(expected = EntityValidationException.class)
    public void testValidation() throws Exception {
        Shipment shipment = new Shipment();
        shipment.setQuantityImported(-5);
        shipment.setDateImported(null);
        shipment.setItem(null);
        repository.create(shipment);
    }

    @Test
    public void dateFilteringTestFrom() throws Exception {
        Shipment shipment = new Shipment();
        shipment.setQuantityImported(5);
        shipment.setDateImported(date(550*1000L));
        shipment.setItem(item);
        repository.create(shipment);

        ShipmentFilter filter = new ShipmentFilter();
        filter.setDateImportedFrom(date(300*1000L));
        Iterable<Shipment> result = repository.find(filter);
        assertThat(result.spliterator().getExactSizeIfKnown(), is(2L));
        assertThat(result, hasItems(shipment, insertedShipment));
    }

    @Test
    public void dateFilteringTestFromTo() throws Exception {
        Shipment shipment = new Shipment();
        shipment.setQuantityImported(5);
        shipment.setDateImported(date(550*1000L));
        shipment.setItem(item);
        repository.create(shipment);

        ShipmentFilter filter = new ShipmentFilter();
        filter.setDateImportedFrom(date(300*1000L));
        filter.setDateImportedTo(date(580*1000L));
        Iterable<Shipment> result = repository.find(filter);
        assertThat(result.spliterator().getExactSizeIfKnown(), is(1L));
        assertThat(result, hasItems(shipment));
    }
}
