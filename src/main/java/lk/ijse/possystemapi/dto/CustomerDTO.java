package lk.ijse.possystemapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO {
    private String Custid;
    private String CustName;
    private String CustAddress;
    private String CustContact;
}
