package entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Item {
    private String code;
    private String description;
    private double unitPrice;
    private int qtyOnHand;
}
