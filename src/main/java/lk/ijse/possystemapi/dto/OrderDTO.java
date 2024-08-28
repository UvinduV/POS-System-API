package lk.ijse.possystemapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDTO implements Serializable {
    private String orderId;
    private String customerId;
    private Date orderDate;
    private double total;
}
