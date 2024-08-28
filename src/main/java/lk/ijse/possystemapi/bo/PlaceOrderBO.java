package lk.ijse.possystemapi.bo;

import lk.ijse.possystemapi.dto.CustomerDTO;
import lk.ijse.possystemapi.dto.PlaceOrderDTO;

import java.sql.Connection;

public interface PlaceOrderBO {
    boolean placeOrder(PlaceOrderDTO placeOrderDTO, Connection connection);
}
