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
import pb138.service.overview.OverviewResult;

import javax.annotation.PostConstruct;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.transaction.Transactional;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.Date;
import java.util.List;


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
    //@Autowired //TODO: once done, autowire
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

    @WebMethod(exclude = true)
    @PostConstruct
    public void postConstruct() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @WebMethod
    public void addTestData() throws EntityValidationException {
        Category cat = new Category();
        cat.setDescription("desc");
        cat.setName("cat name");
        categoryRepository.create(cat);

        Item item = new Item();
        item.setDescription("i desc");
        item.setName("i name0");
        item.setCategory(cat);
        item.setCurrentCount(25);
        item.setAlertThreshold(30);
        item.setEan(123);
        item.setUnit("gram");
        itemRepository.create(item);

        Item item1 = new Item();
        item1.setDescription("i desc");
        item1.setName("i name1");
        item1.setCategory(cat);
        item1.setCurrentCount(25);
        item1.setAlertThreshold(30);
        item1.setEan(124);
        item1.setUnit("kg");
        itemRepository.create(item1);

        Item item2 = new Item();
        item2.setDescription("i desc");
        item2.setName("i name2");
        item2.setCategory(cat);
        item2.setCurrentCount(10);
        item2.setAlertThreshold(30);
        item2.setEan(125);
        item2.setUnit("pcs");
        itemRepository.create(item2);

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

    @WebMethod
    public String testCorrectDeployment(@WebParam String input) {
        int counter = 0;
        counter += categoryRepository != null ? 1 : 0;
        counter += itemRepository != null ? 1 : 0;
        counter += saleRepository != null ? 1 : 0;
        counter += shipmentRepository != null ? 1 : 0;
        return "ECHO: " + input + "\r\nValid entities: " + counter + " / 4";
    }


    @WebMethod
    public void importXml(@WebParam String xmlToImport) throws ServiceException, EntityDoesNotExistException, NotEnoughStoredException, XmlValidationException {
        xmlImporter.importXml(xmlToImport);
    }

    @WebMethod
    public String exportAllItemsToXml() throws TransformerException, ParserConfigurationException {
        return xmlExporter.ExportXmlToString();
    }

    @WebMethod
    public List<ItemDto> getAllItems() {
        List<Item> allItems = itemFacade.getAllItems();
        return automapper.mapTo(allItems, ItemDto.class);
    }

    @WebMethod
    public List<ItemDto> getAllItemsForCategory(@WebParam String categoryName) throws EntityDoesNotExistException {
        List<Item> allItems = itemFacade.getAllItemsByCategory(categoryName);
        return automapper.mapTo(allItems, ItemDto.class);
    }

    @WebMethod
    public List<CategoryDto> getAllCategories() {
        List<Category> allCategories = categoryFacade.getAllCategories();
        return automapper.mapTo(allCategories, CategoryDto.class);
    }

    @WebMethod
    public CategoryDto getCategoryByName(@WebParam String name) {
        Category category = categoryFacade.getCategoryByName(name);
        return automapper.mapTo(category, CategoryDto.class);
    }

    @WebMethod
    public List<OverviewResult> getDailySalesForItem(long ean, Date dayStart, int numberOfDays) {
        return overviewProvider.getDailySalesForItem(itemFacade.getItemByEan(ean), dayStart, numberOfDays);
    }

    @WebMethod
    public List<OverviewResult> getWeeklySalesForItem(long ean, Date weekStart, int numberOfWeeks) {
        return overviewProvider.getWeeklySalesForItem(itemFacade.getItemByEan(ean), weekStart, numberOfWeeks);
    }

    @WebMethod
    public List<OverviewResult> getMonthlySalesForItem(long ean, Date monthStart, int numberOfMonths) {
        return overviewProvider.getMonthlySalesForItem(itemFacade.getItemByEan(ean), monthStart, numberOfMonths);
    }

    @WebMethod
    public List<OverviewResult> getDailySalesForCategory(String category, Date dayStart, int numberOfDays){
        return overviewProvider.getDailySalesForCategory(categoryFacade.getCategoryByName(category), dayStart, numberOfDays);
    }

    @WebMethod
    public List<OverviewResult> getWeeklySalesForCategory(String category, Date weekStart, int numberOfWeeks){
        return overviewProvider.getWeeklySalesForCategory(categoryFacade.getCategoryByName(category), weekStart, numberOfWeeks);
    }

    @WebMethod
    public List<OverviewResult> getMonthlySalesForCategory(String category, Date monthStart, int numberOfMonths){
        return overviewProvider.getMonthlySalesForCategory(categoryFacade.getCategoryByName(category), monthStart, numberOfMonths);
    }

    @WebMethod
    public ItemDto changeItem(long ean, int currentCount, String unit, Integer alertThreshold) throws ServiceException, EntityDoesNotExistException {
        Item item = itemFacade.updateItemFromWeb(ean, currentCount, alertThreshold, unit);
        return automapper.mapTo(item, ItemDto.class);
    }

}
