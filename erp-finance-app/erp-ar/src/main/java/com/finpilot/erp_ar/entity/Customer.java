package com.finpilot.erp_ar.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.finpilot.erp_ar.enums.CustomerStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "customer_code", unique = true, nullable = false)
    private String customerCode;

    @Column(nullable = false)
    private String name;

    private String email;
    private String phone;
    private String altPhone;

    @Column(name = "billing_address", columnDefinition = "TEXT")
    private String billingAddress;

    @Column(name = "shipping_address", columnDefinition = "TEXT")
    private String shippingAddress;

    @Column(name = "tax_id")
    private String taxId;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "payment_terms")
    private String paymentTerms; // NET30, NET45, etc.

    @Column(name = "credit_limit", precision = 12, scale = 2)
    private BigDecimal creditLimit;

    @Column(name = "balance_due", precision = 12, scale = 2)
    private BigDecimal balanceDue;

    @Column(nullable = false)
    private String status; // ACTIVE, INACTIVE, BLACKLISTED

    @Column(columnDefinition = "TEXT")
    private String notes;

    private String approvedBy;
    private LocalDateTime approvedAt;
    private String rejectionReason;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Invoice> invoices = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CustomerStatusAudit> customerStatusAudits = new ArrayList<>();
}
