package com.finpilot.erp_ap.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @Column(nullable = false, unique = true, length = 100)
    private String paymentNumber;

    @Column(nullable = false)
    private LocalDate paymentDate;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(length = 3)
    private String currency;

    @Column(length = 50)
    private String method; // BANK_TRANSFER, CHEQUE, CASH, etc.

    @Column(length = 100)
    private String bankReference;

    @Column(precision = 15, scale = 2)
    private BigDecimal fees;

    @Column(length = 30)
    private String status; // INITIATED, COMPLETED, FAILED, CANCELLED

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentBillMapping> allocations = new ArrayList<>();
}
