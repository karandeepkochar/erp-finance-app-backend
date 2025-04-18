package com.finpilot.erp_ar.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CustomerApproveRequestDTO {
    private Long customerId;
    private String approvedBy;
    private String remarks;
}
