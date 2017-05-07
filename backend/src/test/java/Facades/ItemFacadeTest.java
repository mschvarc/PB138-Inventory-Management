package Facades;

import javafx.util.Pair;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pb138.dal.entities.Category;
import pb138.dal.entities.Item;
import pb138.dal.repository.CategoryRepository;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.facades.CreateOrUpdate;
import pb138.service.facades.ItemFacade;

import javax.transaction.Transactional;
import javax.validation.constraints.Null;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;



/**
 * Created by Jan on 07.05.2017.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/persistence-config.xml")
@Transactional
public class ItemFacadeTest  extends TestCase{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemFacade itemFacade;

    @Before
    public void before() throws Exception {
        Category c1 = new Category();
        c1.setName("Electronics");
        c1.setDescription("Things like TV, PC, etc");
        categoryRepository.create(c1);

        Category c2 = new Category();
        c2.setName("Clothes");
        c2.setDescription("T-shirts, pants, jackets...");
        categoryRepository.create(c2);
    }

    @Test
    public void createCorrectly() throws Exception{
        Pair<Item, CreateOrUpdate> i1 = itemFacade.createOrUpdateItem("PC", "Awesome gaming computer", "Electronics", null, "pieces", 213);
        itemFacade.storeItemInDb(i1);

        List<Item> result = itemFacade.getAllItems();
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), i1.getKey());
    }

    @Test
    public void updateCorrectly() throws Exception {
        Pair<Item, CreateOrUpdate> i1 = itemFacade.createOrUpdateItem("PC", "Awesome gaming computer", "Electronics", null, "pieces", 213);
        itemFacade.storeItemInDb(i1);

        Pair<Item, CreateOrUpdate> i2= itemFacade.createOrUpdateItem("t-shirt", "Nice blue T-Shirt", "Clothes", null, "pieces", 213);
        itemFacade.storeItemInDb(i2);
        List<Item> result = itemFacade.getAllItems();
        assertEquals(i2.getValue(), CreateOrUpdate.UPDATE);
        assertEquals(result.get(0).getDescription(), "Nice blue T-Shirt");

    }

    @Test(expected = EntityDoesNotExistException.class)
    public void updateWithBadCategory() throws Exception {
        Pair<Item, CreateOrUpdate> i1 = itemFacade.createOrUpdateItem("PC", "Awesome gaming computer", "Electronics", null, "pieces", 213);
        itemFacade.storeItemInDb(i1);
        Pair<Item, CreateOrUpdate> i2 = itemFacade.createOrUpdateItem("t-shirt", "Nice blue T-Shirt", "Shirts", null, "pieces", 213);
        itemFacade.storeItemInDb(i2);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void createWithBadCategory() throws Exception {
        Pair<Item, CreateOrUpdate> i1 = itemFacade.createOrUpdateItem("PC", "Awesome gaming computer", "Computers", null, "pieces", 213);
    }

    @Test
    public void getAllByCategory() throws Exception {
        Pair<Item, CreateOrUpdate> i1 = itemFacade.createOrUpdateItem("PC", "Awesome gaming computer", "Electronics", null, "pieces", 213);
        itemFacade.storeItemInDb(i1);

        Pair<Item, CreateOrUpdate> i2 = itemFacade.createOrUpdateItem("Notebook", "Apple iBook", "Electronics", null, "pieces", 35);
        itemFacade.storeItemInDb(i2);

        Pair<Item, CreateOrUpdate> i3 = itemFacade.createOrUpdateItem("PC", "Awesome gaming computer", "Electronics", null, "pieces", 2013);
        itemFacade.storeItemInDb(i3);

        Pair<Item, CreateOrUpdate> i4 = itemFacade.createOrUpdateItem("t-shirt", "Nice blue T-Shirt", "Clothes", null, "pieces", 21312);
        itemFacade.storeItemInDb(i4);

        List<Item> result = itemFacade.getAllItemsByCategory("Electronics");
        assertEquals(result.size(), 3);
        assertThat(result.contains(i2.getKey()), is(true));
        assertThat(result.contains(i4.getKey()), is(false));

    }

    @Test
    public void getByNonExistingEan() {
        Item i = itemFacade.getItemByEan(123);
        assertNull(i);
    }
}
