package pb138.dal.repository;

import pb138.dal.entities.Sale;
import pb138.service.filters.SaleFilter;

public interface SaleRepository {
    Sale getById(long id);

    void create(Sale sale);

    void update(Sale sale);

    void delete(Sale sale);

    Iterable<Sale> find(SaleFilter criteria);
}
