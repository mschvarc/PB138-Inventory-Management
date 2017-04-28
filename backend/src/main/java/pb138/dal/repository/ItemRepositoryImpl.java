package pb138.dal.repository;

import pb138.dal.entities.Item;
import pb138.dal.entities.Item_;
import pb138.dal.repository.validation.ConstraintValidator;
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.filters.ItemFilter;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

public class ItemRepositoryImpl implements ItemRepository {

    private final EntityManager entityManager;
    private final ConstraintValidator validator;

    public ItemRepositoryImpl(EntityManager entityManager, ConstraintValidator validator) {
        this.entityManager = entityManager;
        this.validator = validator;
    }

    @Override
    public Item getById(long id) {
        return entityManager.find(Item.class, id);
    }

    @Override
    public void create(Item item) throws EntityValidationException {
        validator.validate(item);
        entityManager.persist(item);
    }

    @Override
    public void update(Item item) throws EntityValidationException {
        validator.validate(item);
        entityManager.merge(item);
    }

    @Override
    public void delete(Item item) throws EntityValidationException {
        validator.validate(item);
        entityManager.remove(item);
    }

    @Override
    public Iterable<Item> find(ItemFilter filter) {
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
            Predicate threshold = builder.lessThan(root.get(Item_.currentCount), root.get(Item_.alertThreshold));
            validPredicates.add(threshold);
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
