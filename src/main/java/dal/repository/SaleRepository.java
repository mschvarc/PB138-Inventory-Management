package dal.repository;

import service.dto.SaleDto;
import service.filters.SaleFilter;

public interface SaleRepository {
    SaleDto getById(long id);

    void save(SaleDto sale);

    void delete(SaleDto sale);

    Iterable<SaleDto> find(SaleFilter criteria);
}
