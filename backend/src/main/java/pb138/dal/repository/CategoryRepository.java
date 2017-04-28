package pb138.dal.repository;

import pb138.dal.entities.Category;
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.filters.CategoryFilter;

public interface CategoryRepository {
    Category getById(long id);

    void create(Category category) throws EntityValidationException;

    void update(Category category) throws EntityValidationException;

    void delete(Category category) throws EntityValidationException;

    Iterable<Category> find(CategoryFilter filter);
}
