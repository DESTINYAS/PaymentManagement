package com.example.service;

import com.example.model.Payment;
import com.example.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test for PaymentService.
 *
 * ✅ Responsibilities:
 * - Ensures payment processing logic is correct
 * - Verifies repository interactions
 * - Tests exception handling
 */
@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    private Payment payment;

    /**
     * Initializes test data before each test case.
     */
    @BeforeEach
    void setUp() {
        payment = new Payment();
        payment.setId(1L);
        payment.setTransactionId("TXN12345");
        payment.setAmount(BigDecimal.valueOf(200.00));
        payment.setCurrency("USD");
    }

    /**
     * ✅ Test: Successfully process a new payment.
     */
    @Test
    void testProcessPayment_Success() {
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment processedPayment = paymentService.processPayment(payment);

        assertNotNull(processedPayment);
        assertEquals(payment.getTransactionId(), processedPayment.getTransactionId());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    /**
     * ✅ Test: Handling an error when saving a payment.
     */
    @Test
    void testProcessPayment_Failure() {
        when(paymentRepository.save(any(Payment.class))).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> paymentService.processPayment(payment));

        assertEquals("Error processing payment", exception.getMessage());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    /**
     * ✅ Test: Successfully retrieve a payment by transaction ID.
     */
    @Test
    void testGetPaymentByTransactionId_Success() {
        when(paymentRepository.findByTransactionId("TXN12345")).thenReturn(Optional.of(payment));

        Payment foundPayment = paymentService.getPaymentByTransactionId("TXN12345");

        assertNotNull(foundPayment);
        assertEquals("TXN12345", foundPayment.getTransactionId());
        verify(paymentRepository, times(1)).findByTransactionId("TXN12345");
    }

    /**
     * ✅ Test: Handling an error when payment is not found.
     */
    @Test
    void testGetPaymentByTransactionId_NotFound() {
        when(paymentRepository.findByTransactionId("TXN99999")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> paymentService.getPaymentByTransactionId("TXN99999"));

        assertEquals("Payment not found for transaction ID: TXN99999", exception.getMessage());
        verify(paymentRepository, times(1)).findByTransactionId("TXN99999");
    }

    /**
     * ✅ Test: Process multiple payments.
     */
    @Test
    void testProcessMultiplePayments() {
        Payment secondPayment = new Payment();
        secondPayment.setTransactionId("TXN67890");
        secondPayment.setAmount(BigDecimal.valueOf(500.00));
        secondPayment.setCurrency("EUR");

        when(paymentRepository.save(any(Payment.class))).thenReturn(payment).thenReturn(secondPayment);

        Payment processedPayment1 = paymentService.processPayment(payment);
        Payment processedPayment2 = paymentService.processPayment(secondPayment);

        assertNotNull(processedPayment1);
        assertNotNull(processedPayment2);
        verify(paymentRepository, times(2)).save(any(Payment.class));
    }

    /**
     * ✅ Test: Deleting a payment successfully.
     */
    @Test
    void testDeletePayment_Success() {
        when(paymentRepository.findByTransactionId("TXN12345")).thenReturn(Optional.of(payment));
        doNothing().when(paymentRepository).delete(payment);

        paymentService.deletePayment("TXN12345");

        verify(paymentRepository, times(1)).delete(payment);
    }

    /**
     * ✅ Test: Deleting a payment that does not exist.
     */
    @Test
    void testDeletePayment_NotFound() {
        when(paymentRepository.findByTransactionId("TXN99999")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> paymentService.deletePayment("TXN99999"));

        assertEquals("Payment not found for transaction ID: TXN99999", exception.getMessage());
    }
}