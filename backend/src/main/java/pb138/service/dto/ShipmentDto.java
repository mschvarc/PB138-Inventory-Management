package pb138.service.dto;


import java.util.Date;

//no unique business key (yet)
public class ShipmentDto {
    private Long id;
    private ItemDto item;
    private int quantityImported;

    private Date dateImported;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemDto getItem() {
        return item;
    }

    public void setItem(ItemDto item) {
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
}
