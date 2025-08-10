package com.finpilot.erp_ap.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "vendor_contacts")
public class VendorContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @Column(length = 100)
    private String name;

    @Column(length = 255)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(length = 50)
    private String role; // e.g., Accounts Manager, Sales Rep
}
