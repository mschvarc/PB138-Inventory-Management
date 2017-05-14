package pb138.dal.repository;

import pb138.dal.repository.validation.EntityValidationException;

/**
 * Represents a repository for entity manipulation and retrieval
 *
 * @param <T> ORM entity
 * @author Martin Schvarcbacher
 */
public interface EntityRepository<T> {
    /**
     * Retrieves entity by id
     *
     * @param id id
     * @return T item
     */
    T getById(long id);

    /**
     * Creates entity in database
     *
     * @param entity entity
     * @throws EntityValidationException on violation
     */
    void create(T entity) throws EntityValidationException;

    /**
     * Updates entity in database
     *
     * @param entity entity
     * @throws EntityValidationException on violation
     */
    void update(T entity) throws EntityValidationException;

    /**
     * Deletes the entity from database
     *
     * @param entity entity
     * @throws EntityValidationException on violation
     */
    void delete(T entity) throws EntityValidationException;


}
