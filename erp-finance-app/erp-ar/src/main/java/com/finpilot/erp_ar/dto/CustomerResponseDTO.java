package com.finpilot.erp_ar.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class CustomerResponseDTO {
    private Long customerId;
    private String name;
    private String customerCode;
    private String contactEmail;
    private String status;
    private BigDecimal balanceDue;
    private String approvedBy;
    private LocalDateTime approvedAt;
    private String rejectionReason;
}
