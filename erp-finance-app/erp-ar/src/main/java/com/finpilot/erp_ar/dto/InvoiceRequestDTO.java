package com.finpilot.erp_ar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class InvoiceRequestDTO {

    @NotNull
    private Long customerId;

    @NotNull
    private LocalDate invoiceDate;

    @NotNull
    private LocalDate dueDate;

    @NotBlank
    private String paymentTerms; // e.g., "NET30"

    @NotNull
    private BigDecimal totalAmount;

    private String remarks;

    @NotEmpty
    private List<InvoiceLineItemDTO> lineItems;

    //private String referenceNumber; // Optional: PO, Sales Order

    private String currency; // Optional: e.g., "USD"
}
