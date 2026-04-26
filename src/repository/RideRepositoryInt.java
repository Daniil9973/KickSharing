package repository;

import enity.Ride;

import java.sql.SQLException;
import java.util.List;


public interface RideRepositoryInt {
    Ride create(Ride ride);
    Ride update(Ride ride);
    boolean delete(int RideId);

    boolean existsById(int RideId);

    Ride findById(int rideId);
    List<Ride> findAll();
}
