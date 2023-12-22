package entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Orders {
    private String id;
    private String date;
    private String customerId;
}
