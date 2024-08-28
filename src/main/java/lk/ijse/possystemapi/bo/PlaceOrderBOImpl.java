package lk.ijse.possystemapi.bo;

import lk.ijse.possystemapi.dto.PlaceOrderDTO;

import java.sql.Connection;

public class PlaceOrderBOImpl implements PlaceOrderBO{
    @Override
    public boolean placeOrder(PlaceOrderDTO placeOrderDTO, Connection connection) {
        return false;
    }
}
