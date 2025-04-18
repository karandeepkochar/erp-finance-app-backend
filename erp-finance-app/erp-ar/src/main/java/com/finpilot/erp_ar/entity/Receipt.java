package com.finpilot.erp_ar.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "receipts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recieptId;

    @Column(name = "receipt_number", unique = true)
    private String receiptNumber;

    private LocalDate receiptDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    @JsonBackReference
    private Payment payment;

    private String deliveredTo;
    private String deliveryMethod;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
