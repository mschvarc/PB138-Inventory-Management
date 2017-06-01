package pb138.service.services;

import pb138.dal.entities.Category;
import pb138.service.exceptions.ServiceException;

import java.util.List;

/**
 * Class for working with categories
 */
public interface CategoryService {
    /**
     * Creates new category
     * @param category Category to be created
     * @return id of created category
     * @throws ServiceException when there are any unexpected problems
     */
    long create(Category category) throws ServiceException;

    /**
     * Deletes category
     * @param category Category to be deleted
     * @throws ServiceException when there are any unexpected problems
     */
    void delete(Category category) throws ServiceException;

    /**
     * Updates category
     * @param category Category to be updated
     * @throws ServiceException when there are any unexpected problems
     */
    void update(Category category) throws ServiceException;

    /**
     * Return category of given id
     * @param id Id of category
     * @return category with this id, NULL if not present
     */
    Category getById(long id);

    /**
     * Return category of given name
     * @param name Name of category
     * @return category with this name, NULL if not present
     */
    Category getByName(String name);

    /**
     * Returns list of all categories
     * @return list of all categories
     */
    List<Category> getAllCategories();
}
