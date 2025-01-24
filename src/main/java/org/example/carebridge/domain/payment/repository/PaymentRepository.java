package org.example.carebridge.domain.payment.repository;

import org.example.carebridge.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("select p from Payment p where p.orderId = :orderId")
    Payment findByOrderId(String orderId);
}
