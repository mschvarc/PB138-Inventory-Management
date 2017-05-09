package pb138.dal.repository.validation;

/**
 * Validates ORM entity constraints
 */
public interface ConstraintValidator {
    /**
     * Validates entity
     *
     * @param entity input
     * @param <T>    ORM type
     * @throws EntityValidationException on constraint violation
     */
    <T> void validate(T entity) throws EntityValidationException;
}
