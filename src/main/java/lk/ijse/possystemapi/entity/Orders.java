package lk.ijse.possystemapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Orders {
    private String orderId;
    private String customerId;
    private Date orderDate;
    private double total;
}
