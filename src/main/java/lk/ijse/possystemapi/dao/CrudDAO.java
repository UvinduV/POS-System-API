package lk.ijse.possystemapi.dao;

import lk.ijse.possystemapi.entity.Customer;

import java.sql.Connection;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO{
    boolean save(T entity, Connection connection);

    List<T> get(Connection connection);
    boolean update(String customerId, T entity, Connection connection);
    boolean delete(String customerId, Connection connection);
}
