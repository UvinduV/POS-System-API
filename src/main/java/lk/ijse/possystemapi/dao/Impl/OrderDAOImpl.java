package lk.ijse.possystemapi.dao.Impl;

import lk.ijse.possystemapi.dao.Custom.OrderDAO;
import lk.ijse.possystemapi.entity.Orders;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    static String SAVE_Order = "INSERT INTO orders (OrderID,CustomerId,orderDate,total) VALUES (?,?,?,?)";
    @Override
    public boolean save(Orders entity, Connection connection) {
        try {
            var ps = connection.prepareStatement(SAVE_Order);
            ps.setString(1, entity.getOrderId());
            ps.setString(2, entity.getCustomerId());
            ps.setString(3, String.valueOf(entity.getOrderDate()));
            ps.setString(4, String.valueOf(entity.getTotal()));
            System.out.println("dao order"+entity);

            return ps.executeUpdate() !=0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Orders> get(Connection connection) {
        return null;
    }

    @Override
    public boolean update(String customerId, Orders entity, Connection connection) {
        return false;
    }

    @Override
    public boolean delete(String customerId, Connection connection) {
        return false;
    }
}
