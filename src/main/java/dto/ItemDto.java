package dto.tm;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class ItemDto extends RecursiveTreeObject<ItemDto> {
    private String code;
    private String description;
    private Double price;
    private int qty;


    public ItemDto() {
    }

    public ItemDto(String code, String description, Double price, int qty) {
        this.code = code;
        this.description = description;
        this.price = price;
        this.qty = qty;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }



    @Override
    public String toString() {
        return "ItemDto{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", qty=" + qty +
                '}';
    }
}