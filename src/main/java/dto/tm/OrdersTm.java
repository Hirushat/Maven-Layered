package dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrdersTm extends RecursiveTreeObject<OrdersTm> {
    private String id;
    private String date;
    private String custId;
    private JFXButton btn;
}
