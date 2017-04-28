package pb138.dal.repository;

import pb138.dal.entities.Sale;
import pb138.dal.repository.validation.EntityValidationException;
import pb138.service.filters.SaleFilter;

public interface SaleRepository {
    Sale getById(long id);

    void create(Sale sale) throws EntityValidationException;

    void update(Sale sale) throws EntityValidationException;

    void delete(Sale sale) throws EntityValidationException;

    Iterable<Sale> find(SaleFilter filter);
}
