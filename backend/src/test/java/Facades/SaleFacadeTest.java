package Facades;

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
import pb138.service.exceptions.NotEnoughStoredException;
import pb138.service.facades.CategoryFacade;
import pb138.service.facades.CreateOrUpdate;
import pb138.service.facades.ItemFacade;
import pb138.service.facades.SaleFacade;
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
public class SaleFacadeTest extends TestCase {
    @Autowired
    private SaleFacade saleFacade;

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

        itemFacade.updateItemFromWeb(123, 50, null, "pieces");

        Pair<Item, CreateOrUpdate> i2 = itemFacade.createOrUpdateItem("TV", "desc", "Electronics", null, "pieces", 1234);
        itemFacade.storeItemInDb(i2);

        Pair<Item, CreateOrUpdate> i3 = itemFacade.createOrUpdateItem("Shirt", "desc", "Clothes", null, "pieces", 12034);
        itemFacade.storeItemInDb(i3);
        myCalendar = new GregorianCalendar(2017, 5, 8);
    }

    @Test
    public void createSaleCorrectly() throws Exception{
        Sale s = saleFacade.addSale(123, myCalendar.getTime(), 30);
        saleFacade.storeSaleInDb(s);
        Item i = itemFacade.getItemByEan(123);
        assertThat(i.getCurrentCount(), is(20));
    }

    @Test(expected = NotEnoughStoredException.class)
    public void createSaleTooBig() throws Exception {
        Sale s = saleFacade.addSale(123, myCalendar.getTime(), 70);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void createSaleWithNonexistingItem() throws Exception {
        Sale s = saleFacade.addSale(500, myCalendar.getTime(), 70);
    }
}
