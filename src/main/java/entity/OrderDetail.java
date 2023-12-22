package entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetail {
    private String orderId;
    private String itemCode;
    private int qty;
    private double unitPrice;
}
