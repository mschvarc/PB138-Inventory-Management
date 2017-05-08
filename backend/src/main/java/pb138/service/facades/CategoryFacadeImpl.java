package pb138.service.facades;

import pb138.dal.entities.Category;
import pb138.service.exceptions.ServiceException;
import pb138.service.services.CategoryService;

import java.util.List;

/**
 * Created by Jan on 05.05.2017.
 * Implements category facade
 */
public class CategoryFacadeImpl implements CategoryFacade {
    private CategoryService categoryService;

    public CategoryFacadeImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public Category createOrUpdateCategory(String name, String desctription) throws ServiceException {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        if (desctription == null) {
            throw new IllegalArgumentException("Description must not be null");
        }
        Category c = categoryService.getByName(name);
        if (c != null) {
            return changeCategory(c, desctription);
        }
        c = new Category();
        c.setName(name);
        c.setDescription(desctription);
        categoryService.create(c);
        return c;
    }


    @Override
    public Category changeCategory(Category c, String newDescription) throws ServiceException{
        c.setDescription(newDescription);
        categoryService.update(c);
        return c;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryService.getByName(name);
    }


}
