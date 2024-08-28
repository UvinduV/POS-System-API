package lk.ijse.possystemapi.dao.Impl;

import lk.ijse.possystemapi.dao.Custom.OrderDetailDAO;
import lk.ijse.possystemapi.entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    static String SAVE_OrderDetail = "INSERT INTO orderDetail (OrderID,ItemCode,qty,unitPrice) VALUES (?,?,?,?)";
    @Override
    public boolean save(OrderDetail entity, Connection connection) {
        try {
            var ps = connection.prepareStatement(SAVE_OrderDetail);
            ps.setString(1, entity.getOrderId());
            ps.setString(2, entity.getItemCode());
            ps.setString(3, String.valueOf(entity.getQty()));
            ps.setString(4, String.valueOf(entity.getUnitPrice()));
            System.out.println("dao orderDetail"+entity);

            return ps.executeUpdate() !=0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderDetail> get(Connection connection) {
        return null;
    }

    @Override
    public boolean update(String customerId, OrderDetail entity, Connection connection) {
        return false;
    }

    @Override
    public boolean delete(String customerId, Connection connection) {
        return false;
    }
}
