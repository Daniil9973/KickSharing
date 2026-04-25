package repository;

import enity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepositoryInt {

    User create(User user);
    User update(User user);
    void delete(int userId) throws SQLException;
    boolean existsById(int userId);

    User findById(int userId);
    List<User> findAll();
}
