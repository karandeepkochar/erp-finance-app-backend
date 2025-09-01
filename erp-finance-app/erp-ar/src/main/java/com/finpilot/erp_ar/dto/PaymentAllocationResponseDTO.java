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
public class PaymentAllocationResponseDTO {
    private Long allocationId;
    private Long paymentId;
    private BigDecimal allocatedAmount;
}
