package XmlImportExport;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import junit.framework.TestCase;
import org.h2.store.fs.FileUtils;
import org.h2.util.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pb138.dal.entities.Category;
import pb138.service.XmlImportExport.XmlImporter;
import pb138.service.facades.CategoryFacade;

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
public class XmlImporterTest {

    @Autowired
    private XmlImporter xmlImporter;

    @Autowired CategoryFacade categoryFacade;

    @Test
    public void testItems() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String s = Resources.toString(Resources.getResource("xml_schema/examples/example_items.xml"), Charsets.UTF_8);
        xmlImporter.importXml(s);

    }


}
