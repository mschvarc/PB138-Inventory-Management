package service.services;

import service.dto.CategoryDto;

public interface CategoryService {
    int createCategory(CategoryDto category);

    void deleteCategory(CategoryDto category);

    void updateCategory(CategoryDto category);

    CategoryDto getCategoryById(long id);
}
