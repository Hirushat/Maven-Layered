package entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class OrderDetail {
    @Id
    private String orderId;
    private String itemCode;
    private int qty;
    private double unitPrice;
}
