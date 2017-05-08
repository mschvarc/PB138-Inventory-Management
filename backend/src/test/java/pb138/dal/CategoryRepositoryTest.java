package pb138.dal;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pb138.dal.entities.Category;
import pb138.dal.repository.CategoryRepository;
import pb138.dal.repository.validation.ConstraintValidator;
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.filters.CategoryFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/persistence-config.xml")
@Transactional
public class CategoryRepositoryTest extends TestCase {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ConstraintValidator validator;

    private Category insertedCategory;

    @Transactional
    @Before
    public void beforeTest() throws Exception {
        insertedCategory = new Category();
        insertedCategory.setDescription("desc");
        insertedCategory.setName("cat_name");
        validator.validate(insertedCategory);
        manager.persist(insertedCategory);
    }


    @Test
    public void basicCreateRetrieveTest() throws Exception {
        Category category = new Category();
        category.setName("name");
        category.setDescription("desc");
        repository.create(category);
        Category result = repository.getById(category.getId());
        assertNotNull(result);
        assertEquals(result.getId(), category.getId());
    }

    @Test(expected = EntityValidationException.class)
    public void duplicateKeyTest() throws Exception {
        Category c1 = new Category();
        c1.setName("name");
        c1.setDescription("desc1");
        repository.create(c1);

        Category c2 = new Category();
        c2.setName("name");
        c2.setDescription("desc2");
        repository.create(c2);
    }


    @Test
    public void basicFilteringTest() throws Exception {
        Category category = new Category();
        category.setDescription("dd");
        category.setName("category_name1");
        repository.create(category);
        CategoryFilter filter = new CategoryFilter();
        filter.setName("category_name1");
        filter.setId(category.getId());

        Iterable<Category> result = repository.find(filter);
        assertThat(result.spliterator().getExactSizeIfKnown(), is(1L));
        assertThat(result, hasItem(category));
    }

    @Test
    public void multipleCategoryFilter() throws Exception {
        Category category1 = new Category();
        category1.setDescription("dd");
        category1.setName("category_name1");
        repository.create(category1);

        Category category2 = new Category();
        category2.setDescription("dd");
        category2.setName("category_name2");
        repository.create(category2);

        Category category3 = new Category();
        category3.setDescription("dd");
        category3.setName("category_name3");
        repository.create(category3);

        CategoryFilter filter = new CategoryFilter();

        Iterable<Category> result = repository.find(filter);
        assertThat(result.spliterator().getExactSizeIfKnown(), is(4L));
        assertThat(result, hasItem(category1));
    }


    @Test
    public void basicRetrieveTest() {
        Category result = repository.getById(insertedCategory.getId());
        assertThat(result, is(insertedCategory));
    }


    @Test(expected = EntityValidationException.class)
    public void testValidation() throws Exception {
        Category category = new Category();
        category.setDescription(null);
        category.setName(null);
        repository.create(category);
    }

    @Test
    public void updateTest() throws Exception {
        Category category = new Category();
        category.setName("name");
        category.setDescription("desc");
        repository.create(category);
        Category result = repository.getById(category.getId());
        assertNotNull(result);
        result.setDescription("desc updated");
        repository.update(result);
        result = repository.getById(result.getId());
        assertThat(result.getDescription(), is(equalTo("desc updated")));
        assertEquals(result, category);
    }

    @Test
    public void deleteTest() throws Exception {
        Category category = new Category();
        category.setName("name");
        category.setDescription("desc");
        repository.create(category);
        Category result = repository.getById(category.getId());
        assertNotNull(result);

        repository.delete(category);
        result = repository.getById(result.getId());
        assertNull(result);
    }

}