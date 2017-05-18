package pb138.service.facades;

import pb138.dal.entities.Category;
import pb138.service.exceptions.ServiceException;

import java.util.List;

/**
 * Created by Jan on 05.05.2017.
 * Interface for working with categories
 */
public interface CategoryFacade {
    /**
     * Creates a new category, or updates it if another one with given name exist;
     * @param name name of the category
     * @param desctription description of the category
     * @return Category that was created/updated
     * @throws ServiceException if something goes wrong on the db layer and service layer cannot deal with it
     */
    Category createOrUpdateCategory(String name, String desctription) throws ServiceException;


    /**
     * Get all categories from db.
     * 
     * @return list of all categories
     */
    List<Category> getAllCategories();

    /**
     * Find category with given name in db.
     * 
     * @param name name of category
     * @return category with given name or null if category with this name does not exist
     */
    Category getCategoryByName(String name);

}
