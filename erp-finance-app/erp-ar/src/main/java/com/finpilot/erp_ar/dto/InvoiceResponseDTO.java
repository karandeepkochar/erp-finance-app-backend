package com.finpilot.erp_ar.dto;

import com.finpilot.erp_ar.entity.InvoiceLineItem;
import com.finpilot.erp_ar.enums.InvoiceStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class InvoiceResponseDTO {
    private Long invoiceId;
    private String invoiceNumber;

    private Long customerId;
    private String customerName;

    private LocalDate invoiceDate;
    private LocalDate dueDate;

    private BigDecimal totalAmount;
    private BigDecimal balanceDue;

    private InvoiceStatus status;

    private String remarks;
    private List<InvoiceLineItemResponseDTO> lineItems; // List of InvoiceLineItem objects
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
