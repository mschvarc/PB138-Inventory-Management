package pb138.dal.repository;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import pb138.dal.entities.Item_;
import pb138.dal.entities.Shipment;
import pb138.dal.entities.Shipment_;
import pb138.dal.repository.validation.ConstraintValidator;
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.filters.ShipmentFilter;

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
public class ShipmentRepositoryImpl implements ShipmentRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private final ConstraintValidator validator;

    /**
     * Constructor
     *
     * @param entityManager entityManager
     * @param validator     validator
     */
    public ShipmentRepositoryImpl(EntityManager entityManager, ConstraintValidator validator) {
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
    public Shipment getById(long id) {
        return entityManager.find(Shipment.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Shipment shipment) throws EntityValidationException {
        try {
            validator.validate(shipment);
            entityManager.persist(shipment);
            entityManager.flush();
        } catch (javax.persistence.PersistenceException ex) {
            throw new EntityValidationException("Failed to create entity, check inner exception: " + ex.getCause() + ex.getMessage(), ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Shipment shipment) throws EntityValidationException {
        try {
            validator.validate(shipment);
            entityManager.merge(shipment);
            entityManager.flush();
        } catch (javax.persistence.PersistenceException ex) {
            throw new EntityValidationException("Failed to update entity, check inner exception", ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Shipment shipment) throws EntityValidationException {
        try {
            validator.validate(shipment);
            entityManager.remove(shipment);
            entityManager.flush();
        } catch (javax.persistence.PersistenceException ex) {
            throw new EntityValidationException("Failed to delete entity, check inner exception", ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<Shipment> find(ShipmentFilter filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Shipment> criteria = builder.createQuery(Shipment.class);
        Root<Shipment> root = criteria.from(Shipment.class);
        criteria.select(root);

        if(filter.getDateImported() != null && filter.getDateImportedFrom() != null && filter.getDateImported().before(filter.getDateImportedFrom())){
            throw new IllegalStateException("Filter date imported is before filter date imported from");
        }
        if(filter.getDateImported() != null && filter.getDateImportedTo() != null && filter.getDateImported().after(filter.getDateImportedTo())){
            throw new IllegalStateException("Filter date imported is after filter date imported from");
        }
        if(filter.getDateImportedTo()!= null && filter.getDateImportedFrom() != null && filter.getDateImportedFrom().after(filter.getDateImportedTo())){
            throw new IllegalStateException("Filter date imported from is after filter date imported to");
        }


        List<Predicate> validPredicates = new LinkedList<>();
        if (filter.getId() != null) {
            Predicate id = builder.equal(root.get(Shipment_.id), filter.getId());
            validPredicates.add(id);
        }
        if (filter.getDateImported() != null) {
            Predicate dateSold = builder.equal(root.get(Shipment_.dateImported), filter.getDateImported());
            validPredicates.add(dateSold);
        }
        if (filter.getItem() != null) {
            Predicate item = builder.equal(root.get(Shipment_.item), filter.getItem());
            validPredicates.add(item);
        }
        if (filter.getDateImportedFrom() != null) {
            Predicate dateSoldFrom = builder.greaterThanOrEqualTo(root.get(Shipment_.dateImported), filter.getDateImportedFrom());
            validPredicates.add(dateSoldFrom);
        }
        if (filter.getDateImportedTo() != null) {
            Predicate dateSoldTo = builder.lessThan(root.get(Shipment_.dateImported), filter.getDateImportedTo());
            validPredicates.add(dateSoldTo);
        }
        if (filter.getCategory() != null) {
            //http://stackoverflow.com/questions/6396877/openjpa-criteriabuilder-nested-object-property-fetch
            Predicate category = builder.equal(root.get(Shipment_.item).get(Item_.category), filter.getCategory());
            validPredicates.add(category);
        }
        criteria.where(builder.and(validPredicates.toArray(new Predicate[validPredicates.size()])));
        return entityManager.createQuery(criteria.select(root)).getResultList();
    }
}
