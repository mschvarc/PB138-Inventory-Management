package pb138.service.facades;

import pb138.dal.entities.Category;
import pb138.dal.entities.Item;
import pb138.dal.entities.Sale;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.filters.SaleFilter;
import pb138.service.services.CategoryService;
import pb138.service.services.ItemService;
import pb138.service.services.SaleService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Date;
import java.util.List;

/**
 * Created by Jan on 06.05.2017.
 * Implements SaleFacade
 */
public class SaleFacadeImpl implements SaleFacade {
    private SaleService saleService;
    private ItemService itemService;
    private CategoryService categoryService;

    public SaleFacadeImpl(SaleService saleService, ItemService itemService, CategoryService categoryService) {
        this.saleService = saleService;
        this.itemService = itemService;
        this.categoryService = categoryService;
    }

    @Override
    public Sale addSale(int ean, Date date, int sold) {
        throw new NotImplementedException();
    }

    @Override
    public Sale storeSaleInDb(Sale s) {
        throw new NotImplementedException();
    }

    @Override
    public List<Sale> getSalesForCategory(String categoryName, Date from, Date to) throws EntityDoesNotExistException {
        Category c = categoryService.getByName(categoryName);
        if (c == null) {
            throw new EntityDoesNotExistException("Category " + categoryName + " does not exist");
        }
        SaleFilter filter = new SaleFilter();
        filter.setCategory(c);
        filter.setDateSoldFrom(from);
        filter.setDateSoldTo(to);
        return saleService.getByFilter(filter);
    }

    @Override
    public List<Sale> getSalesForProduct(int ean, Date from, Date to) throws EntityDoesNotExistException{
        Item i = itemService.getByEan(ean);
        if (i == null) {
            throw new EntityDoesNotExistException("Item with ean " + ean + " does not exist");
        }
        SaleFilter filter = new SaleFilter();
        filter.setItem(i);
        filter.setDateSoldFrom(from);
        filter.setDateSoldTo(to);
        return saleService.getByFilter(filter);
    }
}
