package XmlImportExport;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
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
public class ShipmentImportingTest {
    @Autowired
    private XmlImporter xmlImporter;

    @Autowired
    private CategoryFacade categoryFacade;

    @Autowired
    private ItemFacade itemFacade;

    @Autowired
    private ShipmentFacade shipmentFacade;

    @Before
    public void before() throws Exception {
        categoryFacade.createOrUpdateCategory("sweet-stuff", "desc");
        categoryFacade.createOrUpdateCategory("office supplies", "desc");
        Pair<Item, CreateOrUpdate> i1 = itemFacade.createOrUpdateItem("Chocolate", "Milk chocolate", "sweet-stuff", null, "pieces", 7614500010013L);
        itemFacade.storeItemInDb(i1);
        Pair<Item, CreateOrUpdate> i2 = itemFacade.createOrUpdateItem("Paper pack", "for printing", "office supplies", null, "packs", 4960999047034L);
        itemFacade.storeItemInDb(i2);
    }

    @Test
    public void loadShipments() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        String s = Resources.toString(Resources.getResource("xml_schema/examples/example_shipments.xml"), Charsets.UTF_8);
        xmlImporter.importXml(s);
        Item i1 = itemFacade.getItemByEan(7614500010013L);
        assertThat(i1.getCurrentCount(), is(500));
        Item i2 = itemFacade.getItemByEan(4960999047034L);
        assertThat(i2.getCurrentCount(), is(800));
        List<Shipment> shipments = shipmentFacade.getAllShipments();
        assertThat(shipments.size(), is(2));
    }



}
