package Facades;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pb138.dal.entities.Category;
import pb138.service.facades.CategoryFacade;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;



/**
 * Created by Jan on 07.05.2017.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/persistence-config.xml")
@Transactional
public class CategoryFacadeTest extends TestCase {

    @Autowired
    private CategoryFacade categoryFacade;

    @Test
    public void createCorrectly() throws Exception {
        Category c1 = categoryFacade.createOrUpdateCategory("Clothes", "For clothing");
        Category c2 = categoryFacade.createOrUpdateCategory("Cars", "For riding in da hood");
        List<Category> result = categoryFacade.getAllCategories();
        assertThat(result.size(), is(2));
        assertThat(result.contains(c1), is(true));
        assertThat(result.contains(c2), is(true));
        assertThat(result.contains(null), is(false));
    }

    @Test
    public void update() throws Exception {
        Category c1 = categoryFacade.createOrUpdateCategory("Clothes", "For clothing");
        Category c2 = categoryFacade.createOrUpdateCategory("Clothes", "Some other generic description");
        List<Category> result = categoryFacade.getAllCategories();
        assertThat(result.size(), is(1));
        assertEquals(c1,c2);
        assertThat(result.get(0).getDescription(), is("Some other generic description"));

    }


}
