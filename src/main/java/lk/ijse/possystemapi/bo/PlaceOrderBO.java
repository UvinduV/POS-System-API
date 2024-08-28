package lk.ijse.possystemapi.bo;

import lk.ijse.possystemapi.dto.CustomerDTO;
import lk.ijse.possystemapi.dto.PlaceOrderDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface PlaceOrderBO {
    boolean placeOrder(PlaceOrderDTO placeOrderDTO, Connection connection) throws SQLException;
}
