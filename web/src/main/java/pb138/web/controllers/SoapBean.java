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
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.XmlImportExport.XmlExporter;
import pb138.service.XmlImportExport.XmlImporter;
import pb138.service.dto.CategoryDto;
import pb138.service.dto.ItemDto;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.exceptions.NotEnoughStoredException;
import pb138.service.exceptions.ServiceException;
import pb138.service.exceptions.XmlValidationException;
import pb138.service.facades.CategoryFacade;
import pb138.service.facades.ItemFacade;
import pb138.service.facades.SaleFacade;
import pb138.service.facades.ShipmentFacade;
import pb138.service.mapper.Automapper;
import pb138.service.overview.OverviewProvider;
import pb138.service.overview.OverviewResultCategory;
import pb138.service.overview.OverviewResultItem;
import pb138.service.overview.OverviewXmlExporter;

import javax.annotation.PostConstruct;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * SOAP API endpoint
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
    //@Autowired //TODO: auto-wire once implemented
    private OverviewXmlExporter overviewXmlExporter;

    @WebMethod(exclude = true)
    @PostConstruct
    public void postConstruct() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    //TODO: debug only!
    @WebMethod
    public void addTestData() throws EntityValidationException {
        Category cat = new Category();
        cat.setDescription("desc");
        cat.setName("category1");
        categoryRepository.create(cat);

        Item item = new Item();
        item.setDescription("i desc");
        item.setName("item0");
        item.setCategory(cat);
        item.setCurrentCount(25);
        item.setAlertThreshold(30);
        item.setEan(123);
        item.setUnit("gram");
        itemRepository.create(item);

        Item item1 = new Item();
        item1.setDescription("i desc");
        item1.setName("item1");
        item1.setCategory(cat);
        item1.setCurrentCount(25);
        item1.setAlertThreshold(30);
        item1.setEan(124);
        item1.setUnit("kg");
        itemRepository.create(item1);

        Item item2 = new Item();
        item2.setDescription("i desc");
        item2.setName("item2");
        item2.setCategory(cat);
        item2.setCurrentCount(100);
        item2.setAlertThreshold(30);
        item2.setEan(125);
        item2.setUnit("pcs");
        itemRepository.create(item2);

        Item item3NullThreshold = new Item();
        item3NullThreshold.setDescription("i desc");
        item3NullThreshold.setName("item3");
        item3NullThreshold.setCategory(cat);
        item3NullThreshold.setCurrentCount(10);
        item3NullThreshold.setAlertThreshold(null);
        item3NullThreshold.setEan(126);
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



    private static Date date(long time){
        Date v = new Date();
        v.setTime(time);
        return v;
    }

    /*
    ---------
    XML OVERVIEW EXPORTERS
    ---------
     */

    @WebMethod
    public String getDailySalesForItemXml(long ean, Date dayStart, int numberOfDays) throws EntityDoesNotExistException {
        return serializeOverviewResultItem(overviewProvider.getDailySalesForItem(ean, dayStart, numberOfDays));
    }

    @WebMethod
    public String getWeeklySalesForItemXml(long ean, Date weekStart, int numberOfWeeks) throws EntityDoesNotExistException {
        return serializeOverviewResultItem(overviewProvider.getWeeklySalesForItem(ean, weekStart, numberOfWeeks));
    }

    @WebMethod
    public String getMonthlySalesForItemXml(long ean, Date monthStart, int numberOfMonths) throws EntityDoesNotExistException {
        return serializeOverviewResultItem(overviewProvider.getMonthlySalesForItem(ean, monthStart, numberOfMonths));
    }

    @WebMethod
    public String getDailySalesForCategoryXml(String category, Date dayStart, int numberOfDays) throws EntityDoesNotExistException {
        return serializeOverviewResultCategory(overviewProvider.getDailySalesForCategory(category, dayStart, numberOfDays));
    }

    @WebMethod
    public String getWeeklySalesForCategoryXml(String category, Date weekStart, int numberOfWeeks) throws EntityDoesNotExistException {
        return serializeOverviewResultCategory(overviewProvider.getWeeklySalesForCategory(category, weekStart, numberOfWeeks));
    }

    @WebMethod
    public String getMonthlySalesForCategoryXml(String category, Date monthStart, int numberOfMonths) throws EntityDoesNotExistException {
        return serializeOverviewResultCategory(overviewProvider.getMonthlySalesForCategory(category, monthStart, numberOfMonths));
    }

    /*
    ---------
    LIST OVERVIEW EXPORTERS
    ---------
     */

    @WebMethod
    public List<OverviewResultItem> getDailySalesForItem(long ean, Date dayStart, int numberOfDays) throws EntityDoesNotExistException {
        return overviewProvider.getDailySalesForItem(ean, dayStart, numberOfDays);
    }

    @WebMethod
    public List<OverviewResultItem> getWeeklySalesForItem(long ean, Date weekStart, int numberOfWeeks) throws EntityDoesNotExistException {
        return overviewProvider.getWeeklySalesForItem(ean, weekStart, numberOfWeeks);
    }

    @WebMethod
    public List<OverviewResultItem> getMonthlySalesForItem(long ean, Date monthStart, int numberOfMonths) throws EntityDoesNotExistException {
        return overviewProvider.getMonthlySalesForItem(ean, monthStart, numberOfMonths);
    }

    @WebMethod
    public List<OverviewResultCategory> getDailySalesForCategory(String category, Date dayStart, int numberOfDays) throws EntityDoesNotExistException {
        return overviewProvider.getDailySalesForCategory(category, dayStart, numberOfDays);
    }

    @WebMethod
    public List<OverviewResultCategory> getWeeklySalesForCategory(String category, Date weekStart, int numberOfWeeks) throws EntityDoesNotExistException {
        return overviewProvider.getWeeklySalesForCategory(category, weekStart, numberOfWeeks);
    }

    @WebMethod
    public List<OverviewResultCategory> getMonthlySalesForCategory(String category, Date monthStart, int numberOfMonths) throws EntityDoesNotExistException {
        return overviewProvider.getMonthlySalesForCategory(category, monthStart, numberOfMonths);
    }


    private String serializeOverviewResultItem(List<OverviewResultItem> items){
        return overviewXmlExporter.exportItemResultToXml(items);
    }

    private String serializeOverviewResultCategory(List<OverviewResultCategory> category){
        return overviewXmlExporter.exportCategoryResultToXml(category);
    }
}
