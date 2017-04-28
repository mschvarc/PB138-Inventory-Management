package pb138.dal.repository;

import pb138.dal.entities.Item_;
import pb138.dal.entities.Shipment;
import pb138.dal.entities.Shipment_;
import pb138.dal.repository.validation.ConstraintValidator;
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.filters.ShipmentFilter;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

public class ShipmentRepositoryImpl implements ShipmentRepository {

    private final EntityManager entityManager;
    private final ConstraintValidator validator;

    public ShipmentRepositoryImpl(EntityManager entityManager, ConstraintValidator validator) {
        this.entityManager = entityManager;
        this.validator = validator;
    }

    @Override
    public Shipment getById(long id) {
        return entityManager.find(Shipment.class, id);
    }

    @Override
    public void create(Shipment shipment) throws EntityValidationException {
        validator.validate(shipment);
        entityManager.persist(shipment);
    }

    @Override
    public void update(Shipment shipment) throws EntityValidationException {
        validator.validate(shipment);
        entityManager.merge(shipment);
    }

    @Override
    public void delete(Shipment shipment) throws EntityValidationException {
        validator.validate(shipment);
        entityManager.remove(shipment);
    }

    @Override
    public Iterable<Shipment> find(ShipmentFilter filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Shipment> criteria = builder.createQuery(Shipment.class);
        Root<Shipment> root = criteria.from(Shipment.class);
        criteria.select(root);

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
        if (filter.getCategory() != null) {
            //http://stackoverflow.com/questions/6396877/openjpa-criteriabuilder-nested-object-property-fetch
            Predicate category = builder.equal(root.get(Shipment_.item).get(Item_.category), filter.getCategory());
            validPredicates.add(category);
        }
        criteria.where(builder.and(validPredicates.toArray(new Predicate[validPredicates.size()])));
        return entityManager.createQuery(criteria.select(root)).getResultList();
    }
}
