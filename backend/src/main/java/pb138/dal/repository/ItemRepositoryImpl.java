package pb138.dal.repository;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import pb138.dal.entities.Item;
import pb138.dal.entities.Item_;
import pb138.dal.repository.validation.ConstraintValidator;
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.filters.ItemFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

/**
 * {@inheritDoc}
 */
@Component
@Repository
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class ItemRepositoryImpl implements ItemRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private final ConstraintValidator validator;

    /**
     * Constructor
     *
     * @param entityManager entityManager
     * @param validator     validator
     */
    public ItemRepositoryImpl(EntityManager entityManager, ConstraintValidator validator) {
        if (entityManager == null || validator == null) {
            throw new IllegalArgumentException("entitymanager or validator is null");
        }
        this.entityManager = entityManager;
        this.validator = validator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item getById(long id) {
        return entityManager.find(Item.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Item item) throws EntityValidationException {
        try {
            validator.validate(item);
            //manual UNIQUE check
            ItemFilter filter = new ItemFilter();
            filter.setEan(item.getEan());
            if (!find(filter).isEmpty()) {
                throw new EntityValidationException("Failed to create entity, duplicate EAN");
            }
            entityManager.persist(item);
            entityManager.flush();
        } catch (javax.persistence.PersistenceException ex) {
            throw new EntityValidationException("Failed to create entity, check inner exception: " + ex.getCause() + ex.getMessage(), ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Item item) throws EntityValidationException {
        try {
            validator.validate(item);
            entityManager.merge(item);
            entityManager.flush();
        } catch (javax.persistence.PersistenceException ex) {
            throw new EntityValidationException("Failed to update entity, check inner exception", ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Item item) throws EntityValidationException {
        try {
            validator.validate(item);
            entityManager.remove(item);
            entityManager.flush();
        } catch (javax.persistence.PersistenceException ex) {
            throw new EntityValidationException("Failed to delete entity, check inner exception", ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Item> find(ItemFilter filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> criteria = builder.createQuery(Item.class);
        Root<Item> root = criteria.from(Item.class);
        criteria.select(root);

        List<Predicate> validPredicates = new LinkedList<>();
        if (filter.getId() != null) {
            Predicate id = builder.equal(root.get(Item_.id), filter.getId());
            validPredicates.add(id);
        }
        if (filter.getFetchItemsBelowThreshold() != null && filter.getFetchItemsBelowThreshold()) {
            Predicate alertThresholdNotNull = builder.isNotNull(root.get(Item_.alertThreshold));
            Predicate belowThreshold = builder.lessThan(root.get(Item_.currentCount), root.get(Item_.alertThreshold));
            Predicate alertThresholdAboveNegOne = builder.greaterThanOrEqualTo(root.get(Item_.alertThreshold), 0);
            Predicate combined = builder.and(alertThresholdNotNull, belowThreshold,  alertThresholdAboveNegOne);
            validPredicates.add(combined);
        }
        if (filter.getCategory() != null) {
            Predicate category = builder.equal(root.get(Item_.category), filter.getCategory());
            validPredicates.add(category);
        }
        if (filter.getName() != null) {
            Predicate name = builder.equal(root.get(Item_.name), filter.getName());
            validPredicates.add(name);
        }
        if (filter.getEan() != null) {
            Predicate ean = builder.equal(root.get(Item_.ean), filter.getEan());
            validPredicates.add(ean);
        }
        criteria.where(builder.and(validPredicates.toArray(new Predicate[validPredicates.size()])));
        return entityManager.createQuery(criteria.select(root)).getResultList();
    }
}
