package com.finpilot.erp_ar.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CustomerRequestDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String contactPerson;
    @Email
    @NotBlank
    private String contactEmail;
    @NotBlank
    private String contactPhone;
    private String altPhone;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String phone;
    @NotBlank
    private String taxId;
    @NotBlank
    private String billingAddress;
    @NotBlank
    private String shippingAddress;
    @NotBlank
    private String paymentTerms;
    private String status;
    private String notes;
    @NotNull(message = "Credit limit is required")
    private BigDecimal creditLimit;
}
