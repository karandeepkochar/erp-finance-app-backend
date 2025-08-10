package com.finpilot.erp_ap.entity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "vendor_credits")
public class VendorCredit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(length = 3)
    private String currency;

    @Column(length = 255)
    private String reason; // Advance payment, Overpayment refund, etc.

    @Column
    private LocalDate createdDate;

    @Column(length = 30)
    private String status; // AVAILABLE, APPLIED, REFUNDED
}
