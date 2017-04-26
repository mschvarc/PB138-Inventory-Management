package pb138.service.services;

import pb138.dal.entities.Category;

public interface CategoryService {
    int create(Category category);

    void delete(Category category);

    void update(Category category);

    Category getById(long id);
}
