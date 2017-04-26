package pb138.dal.repository;

import pb138.service.dto.CategoryDto;
import pb138.service.filters.CategoryFilter;

public interface CategoryRepository {
    CategoryDto getById(long id);

    void save(CategoryDto category);

    void delete(CategoryDto category);

    Iterable<CategoryDto> find(CategoryFilter criteria);
}
