package entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Customer {
    private String id;
    private String name;
    private String address;
    private double salary;
}
