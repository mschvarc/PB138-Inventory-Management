package pb138.service.XmlImportExport;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import jdk.nashorn.internal.runtime.ECMAException;
import junit.framework.TestCase;
import org.h2.store.fs.FileUtils;
import org.h2.util.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pb138.dal.entities.Category;
import pb138.dal.entities.Item;
import pb138.dal.entities.Shipment;
import pb138.service.XmlImportExport.XmlImporter;
import pb138.service.facades.CategoryFacade;
import pb138.service.facades.CreateOrUpdate;
import pb138.service.facades.ItemFacade;
import pb138.service.facades.ShipmentFacade;
import pb138.utils.Pair;

import javax.transaction.Transactional;
import java.io.InputStream;
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
public class CategoryImporterTest extends TestCase {

    @Autowired
    private CategoryFacade categoryFacade;

    @Autowired
    private XmlImporter xmlImporter;

    @Test
    public void importCategories() throws Exception{
        String s = Resources.toString(Resources.getResource("xml_schema/examples/example_categories.xml"), Charsets.UTF_8);
        xmlImporter.importXml(s);
        List<Category> categories = categoryFacade.getAllCategories();
        assertThat(categories.size(), is(3));
        Category c1 = categoryFacade.getCategoryByName("sweet-stuff");
        assertThat(c1.getDescription(), is("Foodstuffs: chocolate, candies atc."));
    }

    @Test
    public void importWithUpdate() throws Exception {
        categoryFacade.createOrUpdateCategory("sweet-stuff", "desc");
        categoryFacade.createOrUpdateCategory("office supplies", "desc");
        String s = Resources.toString(Resources.getResource("xml_schema/examples/example_categories.xml"), Charsets.UTF_8);
        xmlImporter.importXml(s);
        List<Category> categories = categoryFacade.getAllCategories();
        assertThat(categories.size(), is(3));
        Category c1 = categoryFacade.getCategoryByName("sweet-stuff");
        assertThat(c1.getDescription(), is("Foodstuffs: chocolate, candies atc."));
    }


}
