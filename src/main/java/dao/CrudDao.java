package dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDao<T> extends SuperDao {
    boolean save(T entity) throws SQLException;
    boolean update(T entity) throws SQLException;
    boolean delete(String value) throws SQLException;
    List<T> getAll() throws SQLException;
}
