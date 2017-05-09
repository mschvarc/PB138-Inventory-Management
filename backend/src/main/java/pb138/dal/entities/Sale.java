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
 * ORM Sale
 * PK: id
 */
@Entity
public class Sale {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Item item;
    @NotNull
    @Min(0)
    private int quantitySold;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date dateSold;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sale)) {
            return false;
        }

        Sale sale = (Sale) o;

        if (getQuantitySold() != sale.getQuantitySold()) {
            return false;
        }
        if (getItem() != null ? !getItem().equals(sale.getItem()) : sale.getItem() != null) {
            return false;
        }
        return getDateSold() != null ? getDateSold().equals(sale.getDateSold()) : sale.getDateSold() == null;
    }

    @Override
    public int hashCode() {
        int result = getItem() != null ? getItem().hashCode() : 0;
        result = 31 * result + getQuantitySold();
        result = 31 * result + (getDateSold() != null ? getDateSold().hashCode() : 0);
        return result;
    }
}
