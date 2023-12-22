package dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailsTm extends RecursiveTreeObject<OrderDetailsTm> {
    private String code;
    private String desc;
    private int qty;
    private double amount;
    private JFXButton btn;
}
