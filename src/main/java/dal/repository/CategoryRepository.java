package dal.repository;

import service.dto.CategoryDto;
import service.filters.CategoryFilter;

public interface CategoryRepository {
    CategoryDto getById(long id);

    void save(CategoryDto category);

    void delete(CategoryDto category);

    Iterable<CategoryDto> find(CategoryFilter criteria);
}
