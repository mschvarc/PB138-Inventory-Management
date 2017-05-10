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





    List<Category> getAllCategories();

    Category getCategoryByName(String name);

}
