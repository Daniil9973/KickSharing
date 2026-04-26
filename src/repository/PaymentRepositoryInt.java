package repository;

import enity.Payment;

import java.util.List;

public interface PaymentRepositoryInt {
    Payment create(Payment payment);
    Payment update(Payment payment);
    boolean delete(int paymentId);
    boolean existsById(int paymentId);

    double getTotalPaymentsByUserId(int userId);

    Payment findById(int paymentId);
    List<Payment> findAll();
}
