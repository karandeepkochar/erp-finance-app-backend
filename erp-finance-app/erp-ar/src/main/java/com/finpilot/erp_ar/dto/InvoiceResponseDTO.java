package com.finpilot.erp_ar.dto;

import com.finpilot.erp_ar.enums.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceResponseDTO {

    private Long invoiceId;
    private String invoiceNumber;

    // Customer reference
    private Long customerId;
    private String customerName;  // helpful in UI lists

    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private InvoiceStatus status;

    private BigDecimal totalAmount;
    private BigDecimal taxAmount;
    private BigDecimal discountAmount;
    private String currency;

    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Nested DTO for line items
    private List<InvoiceLineItemResponseDTO> lineItems;

    // Optional: allocations (if needed in this API)
    private List<PaymentAllocationResponseDTO> allocations;
}
