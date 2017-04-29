package pb138.dal.repository.validation;

public interface ConstraintValidator {
    <T> void validate(T entity) throws EntityValidationException;
}
