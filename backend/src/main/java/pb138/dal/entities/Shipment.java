package pb138.dal.entities;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * ORM Shipment
 * PK: id
 * @author Martin Schvarcbacher
 */
@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class Shipment extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Item item;

    @NotNull
    @Min(0)
    private int quantityImported;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date dateImported;


    /**
     * Gets item
     *
     * @return value of item
     */
    public Item getItem() {
        return item;
    }

    /**
     * Sets item
     *
     * @param item item
     */
    public void setItem(Item item) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Shipment)) {
            return false;
        }

        Shipment shipment = (Shipment) o;

        if (getQuantityImported() != shipment.getQuantityImported()) {
            return false;
        }
        if (getItem() != null ? !getItem().equals(shipment.getItem()) : shipment.getItem() != null) {
            return false;
        }
        return getDateImported() != null ?
                getDateImported().equals(shipment.getDateImported()) : shipment.getDateImported() == null;
    }

    @Override
    public int hashCode() {
        int result = getItem() != null ? getItem().hashCode() : 0;
        result = 31 * result + getQuantityImported();
        result = 31 * result + (getDateImported() != null ? getDateImported().hashCode() : 0);
        return result;
    }
}
