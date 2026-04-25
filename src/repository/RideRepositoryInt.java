package repository;

import enity.Ride;

import java.sql.SQLException;
import java.util.List;


public interface RideRepositoryInt {
    Ride create(Ride ride);
    Ride update(Ride ride);
    void delete(int RideId) throws SQLException;

    boolean existsById(int RideId);

    Ride findById(int rideId);
    List<Ride> findAll();
}
