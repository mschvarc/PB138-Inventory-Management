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
import pb138.dal.repository.CategoryRepository;
import pb138.dal.repository.ItemRepository;
import pb138.dal.repository.SaleRepository;
import pb138.dal.repository.ShipmentRepository;
import pb138.dal.repository.validation.ConstraintValidator;
import pb138.dal.repository.validation.EntityValidationException;

import javax.annotation.PostConstruct;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.transaction.Transactional;
import java.util.Date;


@Service
@Component
@WebService(name = "SoapBean", serviceName = "SoapBean", targetNamespace = "pb138.web")
@SOAPBinding(style = SOAPBinding.Style.RPC, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED, use = SOAPBinding.Use.LITERAL)
@ImportResource(locations = "classpath:META-INF/persistence-config.xml")
@Transactional
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

    @WebMethod(exclude = true)
    @PostConstruct
    public void postConstruct() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }


    @WebMethod
    public String testCorrectDeployment(@WebParam String input) {
        int counter = 0;
        counter += categoryRepository != null ? 1 : 0;
        counter += itemRepository != null ? 1 : 0;
        counter += saleRepository != null ? 1 : 0;
        counter += shipmentRepository != null ? 1 : 0;
        return "ECHO: " + input + ";; Valid entities: " + counter + " / 4";
    }

    @WebMethod
    public Category returnCategory(@WebParam String input) {
        Category cat = new Category();
        cat.setDescription("desc");
        cat.setName("cat name");
        return cat;
    }

    @WebMethod
    public Item returnItem(@WebParam String input) {

        Item test = itemRepository.getById(1);

        Category cat = new Category();
        cat.setDescription("desc");
        cat.setName("cat name");

        Item item = new Item();
        item.setDescription("i desc");
        item.setName("i name");
        item.setCategory(cat);
        item.setCurrentCount(25);
        item.setAlertThreshold(30);
        item.setEan(123);
        item.setUnit("kg");

        return item;
    }

    @WebMethod
    public Sale returnSale(@WebParam String input) {
        Category cat = new Category();
        cat.setDescription("desc");
        cat.setName("cat name");

        Item item = new Item();
        item.setDescription("i desc");
        item.setName("i name");
        item.setCategory(cat);
        item.setCurrentCount(25);
        item.setAlertThreshold(30);
        item.setEan(123);
        item.setUnit("kg");

        Sale sale = new Sale();
        sale.setDateSold(new Date());
        sale.setQuantitySold(10);
        sale.setItem(item);

        return sale;
    }

    @WebMethod
    public Category saveCategory(@WebParam String name) throws EntityValidationException {
        Category cat = new Category();
        cat.setDescription("desc " + name);
        cat.setName("cat name: " + name);
        categoryRepository.create(cat);
        return cat;
    }

    @WebMethod
    public Category getCategory(@WebParam int id) throws EntityValidationException {
        return categoryRepository.getById(id);
    }


    @WebMethod
    public Category validateCategory(@WebParam Category category) throws EntityValidationException {
        if (category == null || category.getDescription() == null || category.getName() == null) {
            throw new EntityValidationException("null parameters found: ");
        }
        validator.validate(category);
        return category;
    }

}
