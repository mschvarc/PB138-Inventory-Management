package pb138.service.services;

import pb138.dal.entities.Sale;


public interface SaleService {
    int create(Sale sale);

    void delete(Sale sale);

    void update(Sale sale);

    Sale getById(long id);
}
