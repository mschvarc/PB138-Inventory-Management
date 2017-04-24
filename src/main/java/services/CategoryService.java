package services;

import dal.entities.Category;

public interface CategoryService {
    int createCategory(Category category);

    void deleteCategory(Category category);

    void updateCategory(Category category);
}
