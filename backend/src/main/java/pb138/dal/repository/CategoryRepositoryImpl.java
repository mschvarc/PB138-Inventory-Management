package pb138.dal.repository;

import pb138.dal.entities.Category;
import pb138.dal.entities.Category_;
import pb138.dal.repository.validation.ConstraintValidator;
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.filters.CategoryFilter;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepository {

    private final EntityManager entityManager;
    private final ConstraintValidator validator;


    public CategoryRepositoryImpl(EntityManager entityManager, ConstraintValidator validator) {
        this.entityManager = entityManager;
        this.validator = validator;
    }

    @Override
    public Category getById(long id) {
        return entityManager.find(Category.class, id);
    }

    @Override
    public void create(Category category) throws EntityValidationException {
        validator.validate(category);
        entityManager.persist(category);
    }

    @Override
    public void update(Category category) throws EntityValidationException {
        validator.validate(category);
        entityManager.merge(category);
    }

    @Override
    public void delete(Category category) throws EntityValidationException {
        validator.validate(category);
        entityManager.remove(category);
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
