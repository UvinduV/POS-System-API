package lk.ijse.possystemapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {
    private String ItemId;
    private String ItemName;
    private int qty;
    private double price;
}
