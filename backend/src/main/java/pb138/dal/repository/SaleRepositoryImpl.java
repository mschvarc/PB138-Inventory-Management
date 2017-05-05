package pb138.dal.repository;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import pb138.dal.entities.Item_;
import pb138.dal.entities.Sale;
import pb138.dal.entities.Sale_;
import pb138.dal.repository.validation.ConstraintValidator;
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.filters.SaleFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;


//Source: http://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl/


@Component
@Repository
public class SaleRepositoryImpl implements SaleRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private final ConstraintValidator validator;

    public SaleRepositoryImpl(EntityManager entityManager, ConstraintValidator validator) {
        if (entityManager == null || validator == null) {
            throw new IllegalArgumentException("entitymanager or validator is null");
        }
        this.entityManager = entityManager;
        this.validator = validator;
    }

    @Override
    public Sale getById(long id) {
        return entityManager.find(Sale.class, id);
    }

    @Override
    public void create(Sale sale) throws EntityValidationException {
        try {
            validator.validate(sale);
            entityManager.persist(sale);
            entityManager.flush();
        } catch (javax.persistence.PersistenceException ex) {
            throw new EntityValidationException("Failed to create entity, check inner exception: " + ex.getCause() + ex.getMessage(), ex);
        }
    }

    @Override
    public void update(Sale sale) throws EntityValidationException {
        try {
            validator.validate(sale);
            entityManager.merge(sale);
            entityManager.flush();
        } catch (javax.persistence.PersistenceException ex) {
            throw new EntityValidationException("Failed to update entity, check inner exception", ex);
        }
    }

    @Override
    public void delete(Sale sale) throws EntityValidationException {
        try {
            validator.validate(sale);
            entityManager.remove(sale);
            entityManager.flush();
        } catch (javax.persistence.PersistenceException ex) {
            throw new EntityValidationException("Failed to delete entity, check inner exception", ex);
        }
    }

    @Override
    public Iterable<Sale> find(SaleFilter filter) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Sale> criteria = builder.createQuery(Sale.class);
        Root<Sale> root = criteria.from(Sale.class);
        criteria.select(root);

        List<Predicate> validPredicates = new LinkedList<>();
        if (filter.getId() != null) {
            Predicate id = builder.equal(root.get(Sale_.id), filter.getId());
            validPredicates.add(id);
        }
        if (filter.getDateSold() != null) {
            Predicate dateSold = builder.equal(root.get(Sale_.dateSold), filter.getDateSold());
            validPredicates.add(dateSold);
        }
        if (filter.getDateSoldFrom() != null) {
            Predicate dateSoldFrom = builder.greaterThanOrEqualTo(root.get(Sale_.dateSold), filter.getDateSoldFrom());
            validPredicates.add(dateSoldFrom);
        }
        if (filter.getDateSoldTo() != null) {
            Predicate dateSoldTo = builder.lessThanOrEqualTo(root.get(Sale_.dateSold), filter.getDateSoldTo());
            validPredicates.add(dateSoldTo);
        }
        if (filter.getItem() != null) {
            Predicate item = builder.equal(root.get(Sale_.item), filter.getItem());
            validPredicates.add(item);
        }
        if (filter.getCategory() != null) {
            //http://stackoverflow.com/questions/6396877/openjpa-criteriabuilder-nested-object-property-fetch
            Predicate category = builder.equal(root.get(Sale_.item).get(Item_.category), filter.getCategory());
            validPredicates.add(category);
        }
        criteria.where(builder.and(validPredicates.toArray(new Predicate[validPredicates.size()])));
        return entityManager.createQuery(criteria.select(root)).getResultList();
    }
}
