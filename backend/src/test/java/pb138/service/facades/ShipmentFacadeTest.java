package pb138.service.facades;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pb138.dal.entities.Item;
import pb138.dal.entities.Shipment;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.utils.Pair;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Created by Jan on 07.05.2017.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/persistence-config.xml")
@Transactional
public class ShipmentFacadeTest extends TestCase{

    @Autowired
    private ShipmentFacade shipmentFacade;

    @Autowired
    private ItemFacade itemFacade;

    @Autowired
    private CategoryFacade categoryFacade;

    private Calendar myCalendar;

    @Before
    public void before() throws Exception {
        categoryFacade.createOrUpdateCategory("Electronics", "desc");
        categoryFacade.createOrUpdateCategory("Clothes", "desc");

        Pair<Item, CreateOrUpdate> i1 = itemFacade.createOrUpdateItem("PC", "desc", "Electronics", null, "pieces", 123);
        itemFacade.storeItemInDb(i1);

        Pair<Item, CreateOrUpdate> i2 = itemFacade.createOrUpdateItem("TV", "desc", "Electronics", null, "pieces", 1234);
        itemFacade.storeItemInDb(i2);

        Pair<Item, CreateOrUpdate> i3 = itemFacade.createOrUpdateItem("Shirt", "desc", "Clothes", null, "pieces", 12034);
        itemFacade.storeItemInDb(i3);
         myCalendar = new GregorianCalendar(2017, 5, 8);
    }

    @Test
    public void createShipmentCorrectly() throws Exception{


        Shipment s1 = shipmentFacade.addShipment(123, myCalendar.getTime(), 42);
        shipmentFacade.storeShipmentInDb(s1);
        Item i = itemFacade.getItemByEan(123);
        assertThat(i.getCurrentCount(), is(42));

        Shipment s2 = shipmentFacade.addShipment(123, myCalendar.getTime(), 100);
        shipmentFacade.storeShipmentInDb(s2);
        i = itemFacade.getItemByEan(123);
        assertThat(i.getCurrentCount(), is(142));
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void createWithNonExistingItem() throws Exception{
        Shipment s1 = shipmentFacade.addShipment(22222, myCalendar.getTime(), 42);
    }
}
