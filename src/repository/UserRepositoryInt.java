package repository;

import enity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepositoryInt {

    User create(User user);
    User update(User user);
    boolean delete(int userId);
    boolean existsById(int userId);

    User findById(int userId);
    List<User> findAll();
}
