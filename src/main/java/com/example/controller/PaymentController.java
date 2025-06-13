package com.example.controller;

import com.example.model.Payment;
import com.example.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for managing payments.
 *
 * ✅ Responsibilities:
 * - Handles HTTP requests for payment processing.
 * - Delegates business logic to PaymentService.
 * - Manages exceptions and error responses.
 */
@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Constructor-based dependency injection.
     */
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * ✅ Process a new payment.
     * @param payment Payment request body.
     * @return Processed payment details.
     */
    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        return ResponseEntity.ok(paymentService.processPayment(payment));
    }

    /**
     * ✅ Retrieve a payment by transaction ID.
     * @param transactionId Unique transaction identifier.
     * @return Payment details.
     */
    @GetMapping("/{transactionId}")
    public ResponseEntity<Payment> getPayment(@PathVariable String transactionId) {
        return ResponseEntity.ok(paymentService.getPaymentByTransactionId(transactionId));
    }

    /**
     * ✅ Delete a payment by transaction ID.
     * @param transactionId Unique transaction identifier.
     * @return HTTP status indicating success.
     */
    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deletePayment(@PathVariable String transactionId) {
        paymentService.deletePayment(transactionId);
        return ResponseEntity.ok().build();
    }

    /**
     * ✅ Handle not found exceptions and return 404 status.
     * @param e Exception thrown when entity is not found.
     * @return Error message.
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(RuntimeException e) {
        return e.getMessage();
    }
}
