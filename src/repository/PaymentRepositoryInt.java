package repository;

import enity.Payment;

import java.sql.SQLException;
import java.util.List;

public interface PaymentRepositoryInt {
    Payment create(Payment payment);
    Payment update(Payment payment);
    void delete(int paymentId) throws SQLException;
    boolean existsById(int paymentId);

    double getTotalPaymentsByUserId(int userId);

    Payment findById(int paymentId);
    List<Payment> findAll();
}
