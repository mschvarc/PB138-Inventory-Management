package pb138.dal.repository;

import pb138.service.dto.SaleDto;
import pb138.service.filters.SaleFilter;

public interface SaleRepository {
    SaleDto getById(long id);

    void save(SaleDto sale);

    void delete(SaleDto sale);

    Iterable<SaleDto> find(SaleFilter criteria);
}
