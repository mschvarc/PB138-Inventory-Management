package pb138.service.dto;

import java.io.Serializable;
import java.util.Date;

//no unique business key

/**
 * SaleDto
 */
public class SaleDto implements Serializable {
    private Long id;
    private ItemDto item;
    private int quantitySold;
    private Date dateSold;

    /**
     * Gets id
     *
     * @return value of id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id
     *
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets item
     *
     * @return value of item
     */
    public ItemDto getItem() {
        return item;
    }

    /**
     * Sets item
     *
     * @param item item
     */
    public void setItem(ItemDto item) {
        this.item = item;
    }

    /**
     * Gets quantitySold
     *
     * @return value of quantitySold
     */
    public int getQuantitySold() {
        return quantitySold;
    }

    /**
     * Sets quantitySold
     *
     * @param quantitySold quantitySold
     */
    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    /**
     * Gets dateSold
     *
     * @return value of dateSold
     */
    public Date getDateSold() {
        return dateSold;
    }

    /**
     * Sets dateSold
     *
     * @param dateSold dateSold
     */
    public void setDateSold(Date dateSold) {
        this.dateSold = dateSold;
    }
}
