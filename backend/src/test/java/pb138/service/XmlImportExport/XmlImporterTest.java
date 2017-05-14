package pb138.service.XmlImportExport;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pb138.service.XmlImportExport.XmlImporter;
import pb138.service.facades.CategoryFacade;
import pb138.service.facades.ItemFacade;

import javax.transaction.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Created by Jan on 07.05.2017.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/persistence-config.xml")
@Transactional
public class XmlImporterTest extends TestCase {

    @Autowired
    private XmlImporter xmlImporter;

    @Autowired
    private ItemFacade itemFacade;

    @Test
    public void testAll() throws Exception{
        String categories = Resources.toString(Resources.getResource("xml_schema/examples/example_categories.xml"), Charsets.UTF_8);
        xmlImporter.importXml(categories);
        String items = Resources.toString(Resources.getResource("xml_schema/examples/example_items.xml"), Charsets.UTF_8);
        xmlImporter.importXml(items);
        String shipments = Resources.toString(Resources.getResource("xml_schema/examples/example_shipments2.xml"), Charsets.UTF_8);
        xmlImporter.importXml(shipments);
        String sales = Resources.toString(Resources.getResource("xml_schema/examples/example_sales.xml"), Charsets.UTF_8);
        xmlImporter.importXml(sales);

        assertThat(itemFacade.getItemByEan(4960999047034L).getCurrentCount(), is(775));
        assertThat(itemFacade.getItemByEan(42238302211L).getCurrentCount(), is(80));
        assertThat(itemFacade.getItemByEan(5011898007328L).getCurrentCount(), is(50));



    }


}
