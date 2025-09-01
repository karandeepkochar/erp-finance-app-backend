package com.finpilot.erp_ar.dto;

import com.finpilot.erp_ar.enums.InvoiceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class InvoiceUpdateRequestDTO {

    @NotNull
    private Long invoiceId; // Invoice ID

    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private BigDecimal totalAmount;
    private String remarks;

    private List<InvoiceLineItemDTO> lineItems;

    private InvoiceStatus status; // Optional: "DRAFT", "APPROVED", "CANCELLED"
}
