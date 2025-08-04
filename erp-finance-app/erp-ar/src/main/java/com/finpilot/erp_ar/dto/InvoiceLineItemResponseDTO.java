package com.finpilot.erp_ar.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.finpilot.erp_ar.entity.Invoice;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class InvoiceLineItemResponseDTO {
    private Long invoiceLineItemid;
    private String description;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private BigDecimal taxPercent;
    private BigDecimal discount;
    private BigDecimal lineTotal;
}
