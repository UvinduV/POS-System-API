package lk.ijse.possystemapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
    private String CustId;
    private String CustName;
    private String CustAddress;
    private String CustContact;
}
