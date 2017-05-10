package pb138.dal.repository.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

//Source: https://docs.jboss.org/hibernate/validator/6.0/reference/en-US/html_single

/**
 * {@inheritDoc}
 */
public class ConstraintValidatorImpl implements ConstraintValidator {

    private final Validator validator;

    /**
     * Constructs a default validator
     */
    public ConstraintValidatorImpl() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> void validate(T entity) throws EntityValidationException {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        StringBuilder errors = new StringBuilder();
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            errors.append(constraintViolation.getRootBean())
                    .append(" ")
                    .append(constraintViolation.getMessage())
                    .append(System.lineSeparator());
        }

        if (constraintViolations.size() > 0) {
            throw new EntityValidationException("Failed to validate: "
                    + entity.getClass()
                    + ", caused by: "
                    + errors);
        }
        // NOTE for future: if X.Y is NULL, validator should NOT throw an exception,
        // if it does, check X.hashCode() implementation for null checks
        // https://hibernate.atlassian.net/browse/HV-1013
        // http://stackoverflow.com/questions/30333779/javax-validation-validationexception-hv000041-call-to-traversableresolver-isre

    }
}
