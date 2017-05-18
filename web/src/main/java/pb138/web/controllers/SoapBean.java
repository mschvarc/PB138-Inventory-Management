/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import pb138.dal.repository.validation.ConstraintValidator;
import pb138.service.XmlImportExport.XmlExporter;
import pb138.service.XmlImportExport.XmlImporter;
import pb138.service.dto.CategoryDto;
import pb138.service.dto.ItemDto;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.exceptions.NotEnoughStoredException;
import pb138.service.exceptions.ServiceException;
import pb138.service.exceptions.XmlValidationException;
import pb138.service.facades.CategoryFacade;
import pb138.service.facades.CreateOrUpdate;
import pb138.service.facades.ItemFacade;
import pb138.service.facades.SaleFacade;
import pb138.service.facades.ShipmentFacade;
import pb138.service.filters.CategoryFilter;
import pb138.service.filters.ItemFilter;
import pb138.service.filters.SaleFilter;
import pb138.service.filters.ShipmentFilter;
import pb138.service.mapper.Automapper;
import pb138.service.overview.OverviewProvider;
import pb138.service.overview.OverviewResultCategory;
import pb138.service.overview.OverviewResultItem;
import pb138.service.overview.OverviewXmlExporter;
import pb138.utils.Pair;

import javax.annotation.PostConstruct;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * SOAP API endpoint
 *
 * @author Martin Schvarcbacher
 */
@Service
@Component
@WebService(name = "SoapBean", serviceName = "SoapBean", targetNamespace = "pb138.web")
@SOAPBinding(style = SOAPBinding.Style.RPC, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED, use = SOAPBinding.Use.LITERAL)
@ImportResource(locations = "classpath:META-INF/persistence-config.xml")
@Transactional
@SuppressWarnings("unused")
public class SoapBean extends SpringBeanAutowiringSupport {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private ConstraintValidator validator;
    @Autowired
    private OverviewProvider overviewProvider;
    @Autowired
    private CategoryFacade categoryFacade;
    @Autowired
    private ItemFacade itemFacade;
    @Autowired
    private SaleFacade saleFacade;
    @Autowired
    private ShipmentFacade shipmentFacade;
    @Autowired
    private XmlImporter xmlImporter;
    @Autowired
    private XmlExporter xmlExporter;
    @Autowired
    private Automapper automapper;
    @Autowired
    private OverviewXmlExporter overviewXmlExporter;

    @WebMethod(exclude = true)
    @PostConstruct
    public void postConstruct() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    //TODO: debug only!
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

    //TODO: debug only!
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
     * Import XML
     *
     * @param xmlToImport encoded string
     * @throws ServiceException            DB failure
     * @throws EntityDoesNotExistException no such item in DB
     * @throws NotEnoughStoredException    import places stock in the negative
     * @throws XmlValidationException      invalid xml
     */
    @WebMethod
    public void importXml(@WebParam(name = "xmlToImport") String xmlToImport)
            throws ServiceException, EntityDoesNotExistException, NotEnoughStoredException, XmlValidationException {
        xmlImporter.importXml(xmlToImport);
    }

    /**
     * Exports all items from DB
     *
     * @return all items in XML
     * @throws XmlValidationException on internal error
     */
    @WebMethod
    public String exportAllItemsToXml() throws XmlValidationException {
        return xmlExporter.ExportXmlToString();
    }

    /**
     * Returns a list of all items in DB
     *
     * @return all items
     */
    @WebMethod
    public List<ItemDto> getAllItems() {
        List<Item> allItems = itemFacade.getAllItems();
        return automapper.mapTo(allItems, ItemDto.class);
    }

    /**
     * Gets all items in category
     *
     * @param categoryName categoryName
     * @return all items in category
     * @throws EntityDoesNotExistException invalid category
     */
    @WebMethod
    public List<ItemDto> getAllItemsForCategory(@WebParam(name = "categoryName") String categoryName)
            throws EntityDoesNotExistException {
        List<Item> allItems = itemFacade.getAllItemsByCategory(categoryName);
        return automapper.mapTo(allItems, ItemDto.class);
    }

    /**
     * Returns all categories
     *
     * @return all categories
     */
    @WebMethod
    public List<CategoryDto> getAllCategories() {
        List<Category> allCategories = categoryFacade.getAllCategories();
        return automapper.mapTo(allCategories, CategoryDto.class);
    }

    /**
     * Get category by categoryName
     *
     * @param categoryName categoryName
     * @return category
     */
    @WebMethod
    public CategoryDto getCategoryByName(@WebParam(name = "categoryName") String categoryName) {
        Category category = categoryFacade.getCategoryByName(categoryName);
        return automapper.mapTo(category, CategoryDto.class);
    }

    /**
     * Changes the parameters of an item
     *
     * @param ean            ean
     * @param currentCount   current item count
     * @param unit           unit
     * @param alertThreshold alert threshold
     * @return changed item
     * @throws ServiceException            on internal failure
     * @throws EntityDoesNotExistException no such entity
     */
    @WebMethod
    public ItemDto changeItem(
            @WebParam(name = "ean") long ean,
            @WebParam(name = "currentCount") int currentCount,
            @WebParam(name = "unit") String unit,
            @WebParam(name = "alertThreshold") Integer alertThreshold)
            throws ServiceException, EntityDoesNotExistException {
        //convert negative alert threshold to NULL internally
        if (alertThreshold != null && alertThreshold <= 0) {
            alertThreshold = null;
        }
        Item item = itemFacade.updateItemFromWeb(ean, currentCount, alertThreshold, unit);
        return automapper.mapTo(item, ItemDto.class);
    }


    private static Date date(long time) {
        Date v = new Date();
        v.setTime(time);
        return v;
    }

    /*
    ---------
    XML OVERVIEW EXPORTERS
    ---------
     */

    /**
     * Get daily count of sold items.
     *
     * @param ean          - ean of item to find its sales
     * @param dayStart     - date of the first day of the overview
     * @param numberOfDays - number of days of the overview
     * @return XML encoded list of 'OverviewResultItem's with timespan one day
     * @throws EntityDoesNotExistException if item does not exists
     */
    @WebMethod
    public String getDailySalesForItemXml(
            @WebParam(name = "ean") long ean,
            @WebParam(name = "dayStart") Date dayStart,
            @WebParam(name = "numberOfDays") int numberOfDays)
            throws EntityDoesNotExistException, XmlValidationException {
        return serializeOverviewResultItem(getDailySalesForItem(ean, dayStart, numberOfDays));
    }

    /**
     * Get weekly count of sold items.
     *
     * @param ean           - ean of item to find its sales
     * @param weekStart     - date of some day from the first week of the overview
     * @param numberOfWeeks - number of weeks of the overview
     * @return XML encoded list of 'OverviewResultItem's with timespan one week
     * @throws EntityDoesNotExistException if item does not exists
     */
    @WebMethod
    public String getWeeklySalesForItemXml(
            @WebParam(name = "ean") long ean,
            @WebParam(name = "weekStart") Date weekStart,
            @WebParam(name = "numberOfWeeks") int numberOfWeeks)
            throws EntityDoesNotExistException, XmlValidationException {
        return serializeOverviewResultItem(getWeeklySalesForItem(ean, weekStart, numberOfWeeks));
    }

    /**
     * Get monthly count of sold items.
     *
     * @param ean            - ean of item to find its sales
     * @param monthStart     - date of some day from the first month of the overview
     * @param numberOfMonths - number of months of the overview
     * @return XML encoded list of 'OverviewResultItem's with timespan one month
     * @throws EntityDoesNotExistException if item does not exists
     */
    @WebMethod
    public String getMonthlySalesForItemXml(
            @WebParam(name = "ean") long ean,
            @WebParam(name = "monthStart") Date monthStart,
            @WebParam(name = "numberOfMonths") int numberOfMonths)
            throws EntityDoesNotExistException, XmlValidationException {
        return serializeOverviewResultItem(getMonthlySalesForItem(ean, monthStart, numberOfMonths));
    }

    /**
     * Get daily count of sold items from given category.
     *
     * @param category     - name of category to find its sales
     * @param dayStart     - date of the first day of the overview
     * @param numberOfDays - number of days of the overview
     * @return XML encoded list of 'OverviewResultCategory's with timespan one day
     * @throws EntityDoesNotExistException if category does not exists
     */
    @WebMethod
    public String getDailySalesForCategoryXml(
            @WebParam(name = "category") String category,
            @WebParam(name = "dayStart") Date dayStart,
            @WebParam(name = "numberOfDays") int numberOfDays)
            throws EntityDoesNotExistException, XmlValidationException {
        return serializeOverviewResultCategory(getDailySalesForCategory(category, dayStart, numberOfDays));
    }

    /**
     * Get weekly count of sold items from given category.
     *
     * @param category      - name of category to find its sales
     * @param weekStart     - date of some day from the first week of the overview
     * @param numberOfWeeks - number of weeks of the overview
     * @return XML encoded list of 'OverviewResultCategory's with timespan one week
     * @throws EntityDoesNotExistException if category does not exists
     */
    @WebMethod
    public String getWeeklySalesForCategoryXml(
            @WebParam(name = "category") String category,
            @WebParam(name = "weekStart") Date weekStart,
            @WebParam(name = "numberOfWeeks") int numberOfWeeks)
            throws EntityDoesNotExistException, XmlValidationException {
        return serializeOverviewResultCategory(getWeeklySalesForCategory(category, weekStart, numberOfWeeks));
    }

    /**
     * Get monthly count of sold items from given category.
     *
     * @param category       - name of category to find its sales
     * @param monthStart     - date of some day from the first month of the overview
     * @param numberOfMonths - number of months of the overview
     * @return XML encoded list of 'OverviewResultCategory's with timespan one month
     * @throws EntityDoesNotExistException if category does not exists
     */
    @WebMethod
    public String getMonthlySalesForCategoryXml(
            @WebParam(name = "category") String category,
            @WebParam(name = "monthStart") Date monthStart,
            @WebParam(name = "numberOfMonths") int numberOfMonths)
            throws EntityDoesNotExistException, XmlValidationException {
        return serializeOverviewResultCategory(getMonthlySalesForCategory(category, monthStart, numberOfMonths));
    }

    /*
    ---------
    LIST OVERVIEW EXPORTERS
    ---------
     */

    /**
     * Get daily count of sold items.
     *
     * @param ean          - ean of item to find its sales
     * @param dayStart     - date of the first day of the overview
     * @param numberOfDays - number of days of the overview
     * @return list of 'OverviewResultItem's with timespan one day
     * @throws EntityDoesNotExistException if item does not exists
     */
    @WebMethod
    public List<OverviewResultItem> getDailySalesForItem(
            @WebParam(name = "ean") long ean,
            @WebParam(name = "dayStart") Date dayStart,
            @WebParam(name = "numberOfDays") int numberOfDays)
            throws EntityDoesNotExistException {
        return overviewProvider.getDailySalesForItem(ean, dayStart, numberOfDays);
    }

    /**
     * Get weekly count of sold items.
     *
     * @param ean           - ean of item to find its sales
     * @param weekStart     - date of some day from the first week of the overview
     * @param numberOfWeeks - number of weeks of the overview
     * @return list of 'OverviewResultItem's with timespan one week
     * @throws EntityDoesNotExistException if item does not exists
     */
    @WebMethod
    public List<OverviewResultItem> getWeeklySalesForItem(
            @WebParam(name = "ean") long ean,
            @WebParam(name = "weekStart") Date weekStart,
            @WebParam(name = "numberOfWeeks") int numberOfWeeks)
            throws EntityDoesNotExistException {
        return overviewProvider.getWeeklySalesForItem(ean, weekStart, numberOfWeeks);
    }

    /**
     * Get monthly count of sold items.
     *
     * @param ean            - ean of item to find its sales
     * @param monthStart     - date of some day from the first month of the overview
     * @param numberOfMonths - number of months of the overview
     * @return list of 'OverviewResultItem's with timespan one month
     * @throws EntityDoesNotExistException if item does not exists
     */
    @WebMethod
    public List<OverviewResultItem> getMonthlySalesForItem(
            @WebParam(name = "ean") long ean,
            @WebParam(name = "monthStart") Date monthStart,
            @WebParam(name = "numberOfMonths") int numberOfMonths)
            throws EntityDoesNotExistException {
        return overviewProvider.getMonthlySalesForItem(ean, monthStart, numberOfMonths);
    }

    /**
     * Get daily count of sold items from given category.
     *
     * @param category     - name of category to find its sales
     * @param dayStart     - date of the first day of the overview
     * @param numberOfDays - number of days of the overview
     * @return list of 'OverviewResultCategory's with timespan one day
     * @throws EntityDoesNotExistException if category does not exists
     */
    @WebMethod
    public List<OverviewResultCategory> getDailySalesForCategory(
            @WebParam(name = "category") String category,
            @WebParam(name = "dayStart") Date dayStart,
            @WebParam(name = "numberOfDays") int numberOfDays)
            throws EntityDoesNotExistException {
        return overviewProvider.getDailySalesForCategory(category, dayStart, numberOfDays);
    }

    /**
     * Get weekly count of sold items from given category.
     *
     * @param category      - name of category to find its sales
     * @param weekStart     - date of some day from the first week of the overview
     * @param numberOfWeeks - number of weeks of the overview
     * @return list of 'OverviewResultCategory's with timespan one week
     * @throws EntityDoesNotExistException if category does not exists
     */
    @WebMethod
    public List<OverviewResultCategory> getWeeklySalesForCategory(
            @WebParam(name = "category") String category,
            @WebParam(name = "weekStart") Date weekStart,
            @WebParam(name = "numberOfWeeks") int numberOfWeeks)
            throws EntityDoesNotExistException {
        return overviewProvider.getWeeklySalesForCategory(category, weekStart, numberOfWeeks);
    }

    /**
     * Get monthly count of sold items from given category.
     *
     * @param category       - name of category to find its sales
     * @param monthStart     - date of some day from the first month of the overview
     * @param numberOfMonths - number of months of the overview
     * @return list of 'OverviewResultCategory's with timespan one month
     * @throws EntityDoesNotExistException if category does not exists
     */
    @WebMethod
    public List<OverviewResultCategory> getMonthlySalesForCategory(
            @WebParam(name = "category") String category,
            @WebParam(name = "monthStart") Date monthStart,
            @WebParam(name = "numberOfMonths") int numberOfMonths)
            throws EntityDoesNotExistException {
        return overviewProvider.getMonthlySalesForCategory(category, monthStart, numberOfMonths);
    }


    /**
     * Serializes OverviewResultItem to XML
     *
     * @param items item list
     * @return XML
     */
    private String serializeOverviewResultItem(List<OverviewResultItem> items) throws XmlValidationException {
        return overviewXmlExporter.exportItemResultToXml(items);
    }

    /**
     * Serializes OverviewResultItem to XML
     *
     * @param category category list
     * @return XML
     */
    private String serializeOverviewResultCategory(List<OverviewResultCategory> category) throws XmlValidationException {
        return overviewXmlExporter.exportCategoryResultToXml(category);
    }
}
