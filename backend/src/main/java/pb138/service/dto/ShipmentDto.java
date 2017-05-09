package pb138.service.dto;


import java.io.Serializable;
import java.util.Date;

//no unique business key (yet)

/**
 * ShipmentDto
 */
public class ShipmentDto implements Serializable {
    private Long id;
    private ItemDto item;
    private int quantityImported;
    private Date dateImported;

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
     * Gets quantityImported
     *
     * @return value of quantityImported
     */
    public int getQuantityImported() {
        return quantityImported;
    }

    /**
     * Sets quantityImported
     *
     * @param quantityImported quantityImported
     */
    public void setQuantityImported(int quantityImported) {
        this.quantityImported = quantityImported;
    }

    /**
     * Gets dateImported
     *
     * @return value of dateImported
     */
    public Date getDateImported() {
        return dateImported;
    }

    /**
     * Sets dateImported
     *
     * @param dateImported dateImported
     */
    public void setDateImported(Date dateImported) {
        this.dateImported = dateImported;
    }
}
