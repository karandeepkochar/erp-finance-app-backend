package com.finpilot.erp_ar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
