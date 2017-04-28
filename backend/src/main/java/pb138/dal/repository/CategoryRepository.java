package pb138.dal.repository;

import pb138.dal.entities.Category;
import pb138.service.filters.CategoryFilter;

public interface CategoryRepository {
    Category getById(long id);

    void create(Category category);

    void update(Category category);

    void delete(Category category);

    Iterable<Category> find(CategoryFilter filter);
}
