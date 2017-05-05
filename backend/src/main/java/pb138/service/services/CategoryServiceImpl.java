package pb138.service.services;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.hibernate.jpa.internal.EntityManagerImpl;
import pb138.dal.entities.Category;
import pb138.dal.repository.CategoryRepository;
import pb138.dal.repository.CategoryRepositoryImpl;
import pb138.dal.repository.validation.ConstraintValidatorImpl;
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.exceptions.ServiceException;
import pb138.service.filters.CategoryFilter;

import java.util.List;

/**
 * Created by Honza on 30.04.2017.
 *
 */
public class CategoryServiceImpl implements CategoryService{
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public long create(Category category) throws ServiceException{
        try {
            categoryRepository.create(category);
            return category.getId();
        } catch (EntityValidationException e) {
            throw new ServiceException("Problem with validating the category", e);
        }
    }

    @Override
    public void delete(Category category) throws ServiceException {
        try {
            categoryRepository.delete(category);
        } catch (EntityValidationException e) {
            throw new ServiceException("Problem with validating the entry", e);
        }
    }

    @Override
    public void update(Category category) throws ServiceException {
        try {
            categoryRepository.update(category);
        } catch (EntityValidationException e) {
            throw new ServiceException("Problem with validating the entry", e);
        }
    }

    @Override
    public Category getById(long id) {
        return categoryRepository.getById(id);
    }

    @Override
    public Category getByName(String name) {
        CategoryFilter filter = new CategoryFilter();
        filter.setName(name);
        Iterable<Category> result = categoryRepository.find(filter);
        return Iterables.getFirst(result, null);
    }

    @Override
    public List<Category> getAllCategories() {
        CategoryFilter filter = new CategoryFilter();
        Iterable<Category> result = categoryRepository.find(filter);
        return Lists.newArrayList(result);
    }
}
