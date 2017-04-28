package pb138.dal.entities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Shipment {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @ManyToOne
    @NotNull
    private Item item;

    @NotNull
    private int quantityImported;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date dateImported;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantityImported() {
        return quantityImported;
    }

    public void setQuantityImported(int quantityImported) {
        this.quantityImported = quantityImported;
    }

    public Date getDateImported() {
        return dateImported;
    }

    public void setDateImported(Date dateImported) {
        this.dateImported = dateImported;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shipment)) return false;

        Shipment shipment = (Shipment) o;

        if (getId() != shipment.getId()) return false;
        if (getQuantityImported() != shipment.getQuantityImported()) return false;
        if (!getItem().equals(shipment.getItem())) return false;
        return getDateImported().equals(shipment.getDateImported());
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getItem().hashCode();
        result = 31 * result + getQuantityImported();
        result = 31 * result + getDateImported().hashCode();
        return result;
    }
}