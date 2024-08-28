package lk.ijse.possystemapi.bo.Impl;

import lk.ijse.possystemapi.bo.PlaceOrderBO;
import lk.ijse.possystemapi.dao.Custom.OrderDAO;
import lk.ijse.possystemapi.dao.Custom.OrderDetailDAO;
import lk.ijse.possystemapi.dao.Impl.OrderDAOImpl;
import lk.ijse.possystemapi.dao.Impl.OrderDetailDAOImpl;
import lk.ijse.possystemapi.dto.PlaceOrderDTO;
import lk.ijse.possystemapi.entity.OrderDetail;
import lk.ijse.possystemapi.entity.Orders;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    OrderDAO orderDAO = new OrderDAOImpl();
    OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();
    @Override
    public boolean placeOrder(PlaceOrderDTO placeOrderDTO, Connection connection) throws SQLException {
        try {
            connection.setAutoCommit(false);

            // Save the order
            boolean saveOrder = orderDAO.save(new Orders(placeOrderDTO.getOrderId(), placeOrderDTO.getCustomerId(), placeOrderDTO.getOrderDate(), placeOrderDTO.getTotal()),connection);
            if (!saveOrder) {
                connection.rollback();
                return false;
            }

            // Save the order detail
            boolean saveOrderDetail = orderDetailDAO.save(new OrderDetail(placeOrderDTO.getOrderId(), placeOrderDTO.getItemCode(), placeOrderDTO.getQty(), placeOrderDTO.getUnitPrice()),connection);
            if (!saveOrderDetail) {
                connection.rollback();
                return false;
            }
            
            connection.commit();
            return true;

        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
