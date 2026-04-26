package repository;

import util.Logs;
import enity.Ride;
import connect.DataConnect;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class RideRepository implements RideRepositoryInt{



    private final Connection conn;

    public RideRepository() {
        this.conn = DataConnect.getConnection();
    }

    @Override
    public Ride create(Ride ride) {
        String sql = "INSERT INTO ride(user_id, start_time, end_time, price) VALUES (?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ride.getUserId());
            ps.setTimestamp(2, Timestamp.valueOf(ride.getStartTime()));
            ps.setTimestamp(3, Timestamp.valueOf(ride.getEndTime()));
            ps.setDouble(4, ride.getPrice());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                Logs.error("Ошибка: поездка не создана, нет затронутых строк", null);
                return null;
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ride.setId(generatedKeys.getInt(1));
                    Logs.info("Поездка создана с ID: " + ride.getId() +
                            " для user_id: " + ride.getUserId());
                    return ride;
                } else {
                    Logs.error("Ошибка: не удалось получить сгенерированный ID для поездки", null);
                    return null;
                }
            }

        } catch (SQLException e) {
            Logs.error("Ошибка при создании поездки", e);
            return null;
        }
    }

    @Override
    public Ride findById(int rideId) {
        String sql = "SELECT id, user_id, start_time, end_time, price FROM ride WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rideId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Ride ride = new Ride(
                        rs.getInt("user_id"),
                        rs.getTimestamp("start_time").toLocalDateTime(),
                        rs.getTimestamp("end_time").toLocalDateTime(),
                        rs.getDouble("price")
                );
                ride.setId(rs.getInt("id"));
                return ride;
            }
            return null;
        } catch (SQLException e) {
            Logs.error("Ошибка при поиске поездки с id: " + rideId, e);
            return null;
        }
    }

    @Override
    public List<Ride> findAll() {
        String sql = "SELECT id, user_id, start_time, end_time, price FROM ride ORDER BY id";
        List<Ride> rides = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Ride ride = new Ride(
                        rs.getInt("user_id"),
                        rs.getTimestamp("start_time").toLocalDateTime(),
                        rs.getTimestamp("end_time").toLocalDateTime(),
                        rs.getDouble("price")
                );
                ride.setId(rs.getInt("id"));
                rides.add(ride);
            }
        } catch (SQLException e) {
            Logs.error("Ошибка при получении списка поездок", e);
        }
        return rides;
    }

    @Override
    public Ride update(Ride ride) {

        String sql = "UPDATE ride SET user_id=?, start_time=?, end_time=?, price=? WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ride.getUserId());
            ps.setTimestamp(2, Timestamp.valueOf(ride.getStartTime()));
            ps.setTimestamp(3, Timestamp.valueOf(ride.getEndTime()));
            ps.setDouble(4, ride.getPrice());
            ps.setInt(5, ride.getId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                Logs.info("Поездка обновлена, id: " + ride.getId());
                return ride;
            } else {
                Logs.error("Ошибка: поездка с ID " + ride.getId() + " не найдена для обновления", null);
                return null;
            }

        } catch (SQLException e) {
            Logs.error("Ошибка при обновлении поездки с id:" + ride.getId(), e);
            return null;
        }
    }

    @Override
    public boolean delete(int rideId) {
        String sql = "DELETE FROM ride WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rideId);
            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                Logs.info("Поездка удалена, id: " + rideId);
                return true;
            } else {
                Logs.error("Поездка с id " + rideId + " не найдена для удаления", null);
                return false;
            }
        } catch (SQLException e) {
            Logs.error("Ошибка при удалении поездки. id:" + rideId, e);
            return false;
        }
    }

    @Override
    public boolean existsById(int RideId) {
        String sql = "SELECT 1 FROM ride WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, RideId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            Logs.error("Ошибка при проверке существования пользователя с id: " + RideId, e);
            return false;
        }
    }

}