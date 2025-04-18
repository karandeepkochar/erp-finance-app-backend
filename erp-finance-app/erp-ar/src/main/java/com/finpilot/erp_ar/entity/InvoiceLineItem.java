package com.finpilot.erp_ar.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "invoice_line_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceLineItem extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_line_item_id")
    private Long invoiceLineItemid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    @JsonBackReference
    private Invoice invoice;

    private String itemCode;
    @Column(columnDefinition = "TEXT")
    private String description;

    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private BigDecimal taxPercent;
    private BigDecimal discount;
    private BigDecimal lineTotal;
}
