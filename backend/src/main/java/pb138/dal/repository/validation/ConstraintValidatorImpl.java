package pb138.dal.repository.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

//Source: https://docs.jboss.org/hibernate/validator/6.0/reference/en-US/html_single
public class ConstraintValidatorImpl implements ConstraintValidator {

    private final Validator validator;

    public ConstraintValidatorImpl() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }


    @Override
    public <T> void validate(T entity) throws EntityValidationException {
        try {
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
        } catch (javax.validation.ValidationException navigationException) {
            throw new EntityValidationException("Failed to validate: "
                    + entity.getClass()
                    + ", caused by null navigation errors: "
                    + System.lineSeparator()
                    + navigationException.getMessage(),
                    navigationException);
        }
    }
}
