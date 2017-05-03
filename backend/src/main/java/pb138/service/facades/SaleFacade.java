package pb138.service.facades;

import pb138.dal.entities.Sale;

import java.util.Date;

/**
 * Created by Honza on 30.04.2017.
 *
 */
public interface SaleFacade {
    public Sale addSale(long productId, Date date, int sold);
}
