package pb138.dal.entities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

//no unique business key (yet)

/**
 * ORM Shipment
 * PK: id
 */
@Entity
public class Shipment {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

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
     * Gets id
     *
     * @return value of id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id
     *
     * @param id id
     */
    public void setId(long id) {
        this.id = id;
    }

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