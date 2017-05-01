package pb138.service.services;

import pb138.dal.entities.Category;
import pb138.service.exceptions.ServiceException;

public interface CategoryService {
    long create(Category category) throws ServiceException;

    void delete(Category category) throws ServiceException;

    void update(Category category) throws ServiceException;

    Category getById(long id);
}
