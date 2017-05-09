package pb138.service.dto;


import java.io.Serializable;

/**
 * CategoryDto
 */
public class CategoryDto implements Serializable {
    private String description;
    private String name; //unique business key

    /**
     * Gets description
     *
     * @return value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description
     *
     * @param description description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets name
     *
     * @return value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }
}
