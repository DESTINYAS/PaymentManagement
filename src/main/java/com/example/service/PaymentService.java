package com.example.service;

import com.example.model.Payment;
import com.example.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service class for handling payment operations.
 *
 * ✅ Responsibilities:
 * - Handles business logic for payments.
 * - Calls PaymentRepository for database interactions.
 * - Ensures proper exception handling.
 */
@Service // Marks this class as a service component
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    /**
     * Processes a new payment and saves it to the database.
     *
     * @param payment The payment entity to be saved.
     * @return The saved payment entity.
     * @throws RuntimeException If the payment cannot be processed.
     */
    @Transactional
    public Payment processPayment(Payment payment) {
        try {
            return paymentRepository.save(payment);
        } catch (Exception e) {
            throw new RuntimeException("Error processing payment", e);
        }
    }

    /**
     * Retrieves a payment by its transaction ID.
     *
     * @param transactionId The unique transaction ID.
     * @return The found payment entity.
     * @throws RuntimeException If no payment is found.
     */
    public Payment getPaymentByTransactionId(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Payment not found for transaction ID: " + transactionId));
    }

    /**
     * Deletes a payment by its transaction ID.
     *
     * @param transactionId The unique transaction ID.
     * @throws RuntimeException If no payment is found to delete.
     */
    @Transactional
    public void deletePayment(String transactionId) {
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Payment not found for transaction ID: " + transactionId));

        paymentRepository.delete(payment);
    }
}
