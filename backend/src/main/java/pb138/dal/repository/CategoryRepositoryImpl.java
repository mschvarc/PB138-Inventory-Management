package pb138.dal.repository;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import pb138.dal.entities.Category;
import pb138.dal.entities.Category_;
import pb138.dal.repository.validation.ConstraintValidator;
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.filters.CategoryFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

@Component
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    @PersistenceContext
    private final EntityManager entityManager;
    private final ConstraintValidator validator;


    public CategoryRepositoryImpl(EntityManager entityManager, ConstraintValidator validator) {
        if (entityManager == null || validator == null) {
            throw new IllegalArgumentException("entitymanager or validator is null");
        }
        this.entityManager = entityManager;
        this.validator = validator;
    }

    @Override
    public Category getById(long id) {
        return entityManager.find(Category.class, id);
    }

    @Override
    public void create(Category category) throws EntityValidationException {
        try {
            validator.validate(category);
            entityManager.persist(category);
            entityManager.flush();
        } catch (javax.persistence.PersistenceException ex) {
            throw new EntityValidationException("Failed to create entity, check inner exception: " + ex.getCause() + ex.getMessage(), ex);
        }
    }

    @Override
    public void update(Category category) throws EntityValidationException {
        try {
            validator.validate(category);
            entityManager.merge(category);
            entityManager.flush();
        } catch (javax.persistence.PersistenceException ex) {
            throw new EntityValidationException("Failed to update entity, check inner exception", ex);
        }
    }

    @Override
    public void delete(Category category) throws EntityValidationException {
        try {
            validator.validate(category);
            entityManager.remove(category);
            entityManager.flush();
        } catch (javax.persistence.PersistenceException ex) {
            throw new EntityValidationException("Failed to delete entity, check inner exception", ex);
        }
    }

    @Override
    public Iterable<Category> find(CategoryFilter filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> criteria = builder.createQuery(Category.class);
        Root<Category> root = criteria.from(Category.class);
        criteria.select(root);

        List<Predicate> validPredicates = new LinkedList<>();
        if (filter.getId() != null) {
            Predicate id = builder.equal(root.get(Category_.id), filter.getId());
            validPredicates.add(id);
        }
        if (filter.getName() != null) {
            Predicate name = builder.equal(root.get(Category_.name), filter.getName());
            validPredicates.add(name);
        }
        criteria.where(builder.and(validPredicates.toArray(new Predicate[validPredicates.size()])));
        return entityManager.createQuery(criteria.select(root)).getResultList();
    }
}
