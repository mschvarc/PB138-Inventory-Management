package XmlImportExport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pb138.service.XmlImportExport.XmlImporter;

import javax.transaction.Transactional;



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
        //TODO: fix me
        /*
        ClassLoader classLoader = getClass().getClassLoader();
        String s = Resources.toString(Resources.getResource("xml_schema/examples/example_items.xml"), Charsets.UTF_8);
        xmlImporter.importXml(s);
        */
    }


}
