package repository;

import enity.Payment;
import connect.DataConnect;
import util.Logs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepository implements PaymentRepositoryInt {


    private final Connection conn;

    public PaymentRepository() {
        this.conn = DataConnect.getConnection(); // одно соединение на всю сессию
    }



    @Override
    public Payment create(Payment payment) {
        String sql = "INSERT INTO payment(ride_id, amount, status) VALUES (?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, payment.getRideID());
            ps.setDouble(2, payment.getAmount());
            ps.setString(3, payment.getStatus());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                Logs.error("Ошибка: платеж не создан, нет затронутых строк", null);
                return null;
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    payment.setId(generatedKeys.getInt(1));
                    Logs.info("Платёж создан с ID: " + payment.getId() +
                            " для ride_id: " + payment.getRideID());
                    return payment;
                } else {
                    Logs.error("Ошибка: не удалось получить сгенерированный ID", null);
                    return null; // Не удалось получить ID
                }
            }

        } catch (SQLException e) {
            Logs.error("Ошибка при создании Payment", e);
            return null; // Возвращаем null при ошибке
        }
    }

    @Override
    public Payment findById(int paymentId) {
        String sql = "SELECT id, ride_id, amount, status FROM payment WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, paymentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Payment payment = new Payment(
                        rs.getInt("ride_id"),
                        rs.getDouble("amount"),
                        rs.getString("status")
                );
                payment.setId(rs.getInt("id"));
                return payment;
            }
            return null;
        } catch (SQLException e) {
            Logs.error("Ошибка при поиске платежа с id: " + paymentId, e);
            return null;
        }
    }

    @Override
    public List<Payment> findAll() {
        String sql = "SELECT id, ride_id, amount, status FROM payment ORDER BY id";
        List<Payment> payments = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Payment payment = new Payment(
                        rs.getInt("ride_id"),
                        rs.getDouble("amount"),
                        rs.getString("status")
                );
                payment.setId(rs.getInt("id"));
                payments.add(payment);
            }
        } catch (SQLException e) {
            Logs.error("Ошибка при получении списка платежей", e);
        }
        return payments;
    }

    @Override
    public Payment update(Payment payment) {
        String sql = "UPDATE payment SET ride_id=?, amount=?, status=? WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, payment.getRideID());
            ps.setDouble(2, payment.getAmount());
            ps.setString(3, payment.getStatus());
            ps.setInt(4, payment.getId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                Logs.info("Payment обновлён, id: " + payment.getId());
                return payment;
            } else {
                Logs.error("Ошибка: платеж с ID " + payment.getId() + " не найден для обновления", null);
                return null;
            }

        } catch (SQLException e) {
            Logs.error("Ошибка при обновлении Payment. id:" + payment.getId(), e);
            return null;
        }
    }

    @Override
    public boolean delete(int paymentId) {
        String sql = "DELETE FROM payment WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, paymentId);
            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                Logs.info("Payment удалён, id: " + paymentId);
                return true;
            } else {
                Logs.error("Payment с id " + paymentId + " не найден для удаления", null);
                return false;
            }
        } catch (SQLException e) {
            Logs.error("Ошибка при удалении Payment. id:" + paymentId, e);
            return false;
        }
    }

    @Override
    public double getTotalPaymentsByUserId(int userId) {
        String sql = """
            SELECT SUM(p.amount) as total
            FROM payment p
            JOIN ride r ON p.ride_id = r.id
            WHERE r.user_id = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            } else {
                return 0.0;
            }
        } catch (SQLException e) {
            Logs.error("Ошибка при подсчёте суммы платежей для user_id=" + userId, e);
            return 0.0;
        }
    }

    @Override
    public boolean existsById(int paymentId){
        String sql = "SELECT 1 FROM payment WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, paymentId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            Logs.error("Ошибка при проверке существования платежа c id: " + paymentId, e);
            return false;
        }
    }


}
