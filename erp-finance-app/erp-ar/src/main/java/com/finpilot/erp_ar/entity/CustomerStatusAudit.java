package com.finpilot.erp_ar.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "customer_status_audit")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerStatusAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private String previousStatus;
    private String newStatus;
    private LocalDateTime updatedAt = LocalDateTime.now();
    private String updatedBy;
    private String remarks;

    @PrePersist
    protected void onCreate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
