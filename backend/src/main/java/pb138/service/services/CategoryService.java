package pb138.service.services;

import pb138.dal.entities.Category;
import pb138.service.exceptions.ServiceException;

import java.util.List;

public interface CategoryService {
    long create(Category category) throws ServiceException;

    void delete(Category category) throws ServiceException;

    void update(Category category) throws ServiceException;

    Category getById(long id);

    Category getByName(String name);

    List<Category> getAllCategories();
}
