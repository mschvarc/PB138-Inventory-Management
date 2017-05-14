package pb138.dal.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Abstract base class for ORM Entities
 * @author Martin Schvarcbacher
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

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
}
