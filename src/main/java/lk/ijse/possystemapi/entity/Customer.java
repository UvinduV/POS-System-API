package lk.ijse.possystemapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
    private String CustomerId;
    private String CustomerName;
    private String CustomerAddress;
    private String CustomerContact;
}
