package com.finpilot.erp_ar.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "payment_reference", unique = true)
    private String paymentReference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PaymentAllocation> allocations = new ArrayList<>();

    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Receipt receipt;

    private LocalDate paymentDate;
    private BigDecimal amount;
    private String currency;
    private String paymentMode;
    private String receivedBy;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
