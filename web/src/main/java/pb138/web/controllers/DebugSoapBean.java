package pb138.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import pb138.dal.entities.Category;
import pb138.dal.entities.Item;
import pb138.dal.entities.Sale;
import pb138.dal.entities.Shipment;
import pb138.dal.repository.CategoryRepository;
import pb138.dal.repository.ItemRepository;
import pb138.dal.repository.SaleRepository;
import pb138.dal.repository.ShipmentRepository;
import pb138.service.facades.CategoryFacade;
import pb138.service.facades.CreateOrUpdate;
import pb138.service.facades.ItemFacade;
import pb138.service.facades.SaleFacade;
import pb138.service.filters.CategoryFilter;
import pb138.service.filters.ItemFilter;
import pb138.service.filters.SaleFilter;
import pb138.service.filters.ShipmentFilter;
import pb138.utils.Pair;

import javax.annotation.PostConstruct;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;

/**
 * SOAP API endpoint for bootstrapping the database with test data
 *
 * @author Martin Schvarcbacher
 */
@Service
@Component
@WebService(name = "DebugSoapBean", serviceName = "DebugSoapBean", targetNamespace = "pb138.web")
@SOAPBinding(style = SOAPBinding.Style.RPC, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED, use = SOAPBinding.Use.LITERAL)
@ImportResource(locations = "classpath:META-INF/persistence-config.xml")
@Transactional
public class DebugSoapBean extends SpringBeanAutowiringSupport {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private CategoryFacade categoryFacade;
    @Autowired
    private ItemFacade itemFacade;
    @Autowired
    private SaleFacade saleFacade;

    /**
     * Spring postConstruct initialization of dependency inversion context
     */
    @WebMethod(exclude = true)
    @PostConstruct
    public void postConstruct() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    /**
     * Removes all data from database
     * @throws Exception on db cleanup failure
     */
    @WebMethod
    public void clearDatabase() throws Exception {
        for (Sale sale : saleRepository.find(new SaleFilter())) {
            saleRepository.delete(sale);
        }
        for (Shipment shipment : shipmentRepository.find(new ShipmentFilter())) {
            shipmentRepository.delete(shipment);
        }
        for (Item item : itemRepository.find(new ItemFilter())) {
            itemRepository.delete(item);
        }
        for (Category category : categoryRepository.find(new CategoryFilter())) {
            categoryRepository.delete(category);
        }
    }

    /**
     * Tests if SOAP controller is correctly deployed
     *
     * @param input string to echo
     * @return original message + debug data
     */
    @WebMethod
    public String testCorrectDeployment(@WebParam(name = "input") String input) {
        int counter = 0;
        counter += categoryRepository != null ? 1 : 0;
        counter += itemRepository != null ? 1 : 0;
        counter += saleRepository != null ? 1 : 0;
        counter += shipmentRepository != null ? 1 : 0;
        return "ECHO: " + input + "\r\nValid entities: " + counter + " / 4";
    }


    /**
     * Adds test data to the database
     * @throws Exception db failure
     */
    @WebMethod
    public void addTestData() throws Exception {
        Category cat = new Category();
        cat.setDescription("Miscellaneous category description");
        cat.setName("Miscellaneous");
        categoryRepository.create(cat);

        Item item = new Item();
        item.setDescription("i desc");
        item.setName("Item 0");
        item.setCategory(cat);
        item.setCurrentCount(25);
        item.setAlertThreshold(30);
        item.setEan(12300);
        item.setUnit("gram");
        itemRepository.create(item);

        Item item1 = new Item();
        item1.setDescription("i desc");
        item1.setName("Item 1");
        item1.setCategory(cat);
        item1.setCurrentCount(25);
        item1.setAlertThreshold(30);
        item1.setEan(12400);
        item1.setUnit("kg");
        itemRepository.create(item1);

        Item item2 = new Item();
        item2.setDescription("i desc");
        item2.setName("Item 2");
        item2.setCategory(cat);
        item2.setCurrentCount(100);
        item2.setAlertThreshold(30);
        item2.setEan(12500);
        item2.setUnit("pcs");
        itemRepository.create(item2);

        Item item3NullThreshold = new Item();
        item3NullThreshold.setDescription("i desc");
        item3NullThreshold.setName("Item 3");
        item3NullThreshold.setCategory(cat);
        item3NullThreshold.setCurrentCount(10);
        item3NullThreshold.setAlertThreshold(null);
        item3NullThreshold.setEan(12600);
        item3NullThreshold.setUnit("kg");
        itemRepository.create(item3NullThreshold);

        Sale sale = new Sale();
        sale.setDateSold(new Date());
        sale.setQuantitySold(10);
        sale.setItem(item2);
        saleRepository.create(sale);

        Shipment shipment = new Shipment();
        shipment.setDateImported(new Date());
        shipment.setQuantityImported(10);
        shipment.setItem(item);
        shipmentRepository.create(shipment);


        //Marketa's code below
        Calendar calendar;
        categoryFacade.createOrUpdateCategory("Electronics", "desc");
        categoryFacade.createOrUpdateCategory("Clothes", "desc");
        calendar = Calendar.getInstance();

        //1200
        Pair<Item, CreateOrUpdate> s1 = itemFacade.createOrUpdateItem("Shirt", "desc", "Clothes", null, "pieces", 1200);
        itemFacade.storeItemInDb(s1);

        //123
        Pair<Item, CreateOrUpdate> i1 = itemFacade.createOrUpdateItem("PC", "desc", "Electronics", null, "pieces", 123);
        itemFacade.storeItemInDb(i1);
        itemFacade.updateItemFromWeb(123, 50, null, "pieces");

        calendar.set(2017, 1, 1);
        Sale s1i1 = saleFacade.addSale(123, calendar.getTime(), 2);
        saleFacade.storeSaleInDb(s1i1);

        calendar.set(2017, 1, 2, 0, 0, 0);
        Sale s2i1 = saleFacade.addSale(123, calendar.getTime(), 1);
        saleFacade.storeSaleInDb(s2i1);

        calendar.set(2017, 1, 7, 23, 59, 59);
        Sale s3i1 = saleFacade.addSale(123, calendar.getTime(), 3);
        saleFacade.storeSaleInDb(s3i1);

        calendar.set(2017, 1, 8);
        Sale s4i1 = saleFacade.addSale(123, calendar.getTime(), 1);
        saleFacade.storeSaleInDb(s4i1);

        calendar.set(2017, 2, 2);
        Sale s5i1 = saleFacade.addSale(123, calendar.getTime(), 1);
        saleFacade.storeSaleInDb(s5i1);

        calendar.set(2017, 0, 1);
        Sale s6i1 = saleFacade.addSale(123, calendar.getTime(), 2);
        saleFacade.storeSaleInDb(s6i1);

        //1234
        Pair<Item, CreateOrUpdate> i2 = itemFacade.createOrUpdateItem("TV", "desc", "Electronics", null, "pieces", 1234);
        itemFacade.storeItemInDb(i2);
        itemFacade.updateItemFromWeb(1234, 50, null, "pieces");

        calendar.set(2017, 1, 1);
        Sale s1i2 = saleFacade.addSale(1234, calendar.getTime(), 3);
        saleFacade.storeSaleInDb(s1i2);

        calendar.set(2017, 1, 5);
        Sale s2i2 = saleFacade.addSale(1234, calendar.getTime(), 1);
        saleFacade.storeSaleInDb(s2i2);

    }


}
