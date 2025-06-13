package com.example.repository;

import com.example.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing payment records.
 *
 * ✅ Responsibilities:
 * - Provides CRUD operations for Payment entities
 * - Enables JPA-based database access
 * - Supports custom query methods if needed
 */
@Repository // Marks this as a Spring Data repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * Finds a payment by its transaction ID.
     *
     * @param transactionId The unique transaction identifier.
     * @return An Optional containing the Payment if found, otherwise empty.
     */
    Optional<Payment> findByTransactionId(String transactionId);
}