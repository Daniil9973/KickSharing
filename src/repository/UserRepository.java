package repository;

import connect.DataConnect;
import enity.User;
import util.Logs;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements UserRepositoryInt{

    private Connection conn;

    public UserRepository() {
        this.conn = DataConnect.getConnection();
    }

    @Override
    public User create(User user) {
        String sql = "INSERT INTO users (name, email, phone) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                Logs.error("Ошибка: пользователь не создан, нет затронутых строк", null);
                return null;
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                    Logs.info("Пользователь создан с ID: " + user.getId() +
                            ", имя: " + user.getName());
                    return user;
                } else {
                    Logs.error("Ошибка: не удалось получить сгенерированный ID для пользователя", null);
                    return null;
                }
            }

        } catch (SQLException e) {
            Logs.error("Ошибка при создании пользователя", e);
            return null;
        }
    }

    @Override
    public User findById(int userId) {
        String sql = "SELECT id, name, email, phone FROM users WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("email"), rs.getString("phone"));
                user.setId(rs.getInt("id"));
                return user;
            }
            return null;
        } catch (SQLException e) {
            Logs.error("Ошибка при поиске пользователя с id: " + userId, e);
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT id, name, email, phone FROM users ORDER BY id";
        List<User> users = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("email"), rs.getString("phone"));
                user.setId(rs.getInt("id"));
                users.add(user);
            }
            } catch (SQLException e) {
            Logs.error("Ошибка при получении списка пользователей", e);
        }
        return users;
    }

    @Override
    public User update(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, phone = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setInt(4, user.getId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                Logs.info("Пользователь обновлён, id: " + user.getId());
                return user;
            } else {
                Logs.error("Ошибка: пользователь с ID " + user.getId() + " не найден для обновления", null);
                return null;
            }

        } catch (SQLException e) {
            Logs.error("Ошибка при обновлении пользователя с id: " + user.getId(), e);
            return null;
        }
    }

    @Override
    public boolean delete(int userId) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                Logs.info("Пользователь удалён, id: " + userId);
                return true;
            } else {
                Logs.error("Пользователь с id " + userId + " не найден для удаления", null);
                return false;
            }
        } catch (SQLException e) {
            Logs.error("Ошибка при удалении пользователя. id:" + userId, e);
            return false;
        }
    }

    @Override
    public boolean existsById(int userId) {
        String sql = "SELECT 1 FROM users WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();  // true если есть запись, false если нет
            }
        } catch (SQLException e) {
            Logs.error("Ошибка при проверке существования пользователя с id: " + userId, e);
            return false;  // при ошибке считаем, что пользователя нет
        }
    }
}