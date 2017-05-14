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
 * ORM Sale
 * PK: id
 * @author Martin Schvarcbacher
 */
@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class Sale extends BaseEntity {

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
