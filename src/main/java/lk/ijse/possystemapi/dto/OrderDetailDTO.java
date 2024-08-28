package lk.ijse.possystemapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetailDTO implements Serializable {
    private String orderId;
    private String itemCode;
    private int qty;
    private double unitPrice;
}
