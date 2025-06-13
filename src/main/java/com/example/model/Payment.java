package com.example.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity representing a payment transaction.
 *
 * ✅ Responsibilities:
 * - Defines the Payment entity structure
 * - Maps to a database table using JPA annotations
 * - Stores details of payment transactions
 *
 * 🔹 Uses Lombok's @Data annotation to generate boilerplate code such as getters, setters, and constructors.
 */

@Entity // Marks this class as a JPA entity
@Table(name = "payments") // Maps to the 'payments' table
@Data// Lombok annotation to generate getters, setters, equals, hashCode, and toString automatically
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates the ID
    private Long id;

    @Column(nullable = false, unique = true)
    private String transactionId; // Unique identifier for each payment transaction

    @Column(nullable = false)
    private BigDecimal amount; // Payment amount

    @Column(nullable = false)
    private String currency; // Currency type (e.g., USD, EUR)

    @Column(nullable = false)
    private LocalDateTime timestamp; // Timestamp of when the payment was made
}