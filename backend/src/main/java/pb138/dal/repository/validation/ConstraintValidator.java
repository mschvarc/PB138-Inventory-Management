package pb138.dal.repository.validation;

/**
 * Validates ORM entity constraints
 * @author Martin Schvarcbacher
 */
public interface ConstraintValidator {
    /**
     * Validates entity
     *
     * @param entity input
     * @param <Entity>    ORM entity type
     * @throws EntityValidationException on constraint violation
     */
    <Entity> void validate(Entity entity) throws EntityValidationException;
}
