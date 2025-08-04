package com.finpilot.erp_ar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class InvoiceLineItemDTO {
    @NotBlank
    private String description;

    @NotNull
    private BigDecimal quantity;

    @NotNull
    private BigDecimal unitPrice;

    private BigDecimal taxRate;

    private BigDecimal lineTotal; // Optional (can calculate server-side)
}
