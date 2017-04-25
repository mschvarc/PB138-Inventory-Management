package service.services;

import service.dto.SaleDto;


public interface SaleService {
    int createSale(SaleDto sale);

    void deleteSale(SaleDto sale);

    void updateSale(SaleDto sale);

    SaleDto getSaleDtoById(long id);
}
