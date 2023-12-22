package dto;

import entity.OrderDetail;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class OrderDto {
    private String orderId;
    private String date;
    private String custId;
    private List<OrderDetail> list;

}
