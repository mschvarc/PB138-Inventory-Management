package services;

import dal.entities.Sale;


public interface SaleService {
    int createSale(Sale sale);

    void deleteSale(Sale sale);

    void updateSale(Sale sale);
}
