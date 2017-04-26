package pb138.service.services;

import pb138.service.dto.SaleDto;


public interface SaleService {
    int createSale(SaleDto sale);

    void deleteSale(SaleDto sale);

    void updateSale(SaleDto sale);

    SaleDto getSaleDtoById(long id);
}
