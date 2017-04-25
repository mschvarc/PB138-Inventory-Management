package service.dto;


import java.util.Date;

public class ShipmentDto {
    private Long id;
    private ItemDto itemDto;
    private int quantityImported;

    private Date dateImported;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemDto getItemDto() {
        return itemDto;
    }

    public void setItemDto(ItemDto itemDto) {
        this.itemDto = itemDto;
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
